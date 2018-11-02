package com.calvin.bookstorecontroller.redis;

import com.calvin.bookstorebasis.entity.BuyerCart;
import com.calvin.bookstorebasis.entity.BuyerItem;
import com.calvin.bookstorebasis.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class BuyerCartRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    // redis 保存的是键(String,buyerCarCalvin)、 值(HashMap)，哈希表里面保存的是 商品Id(键)，购买数量(值)。
    public boolean insertBuyerCartToRedis(BuyerCart buyerCart, String username) {
        boolean result = false;
        String key = "buyerCart" + username;
        // 将传递过来的购物车items打开
        List<BuyerItem> items = buyerCart.getItems();
        if (items.size() > 0) {
            Map<String, String> value = new HashMap<>();
            for (BuyerItem item : items) {
                // 判断已经保存在redis的商品中是否有 新传进来的产品的Id，如果有，应只将amount数量提高。
                if (redisTemplate.opsForHash().hasKey(key, String.valueOf(item.getProduct().getProductionId()))) {
                    Map<String, String> map = getMap(key);
                    String amount = map.get(String.valueOf(item.getProduct().getProductionId()));
                    amount = String.valueOf(Integer.valueOf(amount) + item.getAmount());
                    map.put(String.valueOf(item.getProduct().getProductionId()), amount);
                    // setMap是已经包装好的 redis 键-哈希表 保存函数
                    result = setMap(key, map);
                } else {
                    value.put(String.valueOf(item.getProduct().getProductionId()), String.valueOf(item.getAmount()));
                }
            }
            if (value.size() > 0)
                // setMap是已经包装好的 redis 键-哈希表 保存函数
                result = setMap(key, value);
        }
        return result;
    }

    // 删除键所需，将商品从 redis 中去除，方法较蠢
    public boolean removeProductFromBuyerCart(Product product, String username) {
        boolean result = false;
        String key = "buyerCart" + username;
        // 把redis保存的购物车取出来
        BuyerCart buyerCart = getBuyerCartFromRedis(username);
        List<BuyerItem> items = buyerCart.getItems();
        for (BuyerItem item : items) {
            // 将redis购物车中对应的商品移除
            if (item.getProduct().getProductionId() == product.getProductionId()) {
                items.remove(item);
                break;
            }
        }
        // 必须将buyerCart + username的key移除，不然只是一种无重复的覆盖，没有remove则商品将翻倍
        remove(key);

        // 将redis购物车(移除了商品后)重新再setMap（保存在redis中）
        if (items.size() > 0) {
            Map<String, String> value = new HashMap<>();
            for (BuyerItem item : items) {
                value.put(String.valueOf(item.getProduct().getProductionId()), String.valueOf(item.getAmount()));
            }
            if (value.size() > 0)
                result = setMap(key, value);
        }
        return result;
    }

    // 从redis中取出购物车
    public BuyerCart getBuyerCartFromRedis(String username) {
        final String key = "buyerCart" + username;
        BuyerCart buyerCart = new BuyerCart();
        // 判断键是否存在
        if (exists(key)) {
            // 将哈希表取出(productId - amount)
            Map<String, String> value = getMap(key);
            // 将哈希表的整个集合取出
            Set<Map.Entry<String, String>> entrySet = value.entrySet();
            // 遍历哈希表， 将键保存在 Product， amount 与 Product 保存在 BuyerItem
            for (Map.Entry<String, String> entry : entrySet) {
                Product product = new Product(Integer.valueOf(entry.getKey()));
                BuyerItem item = new BuyerItem(product, Integer.valueOf(entry.getValue()));
                buyerCart.addItem(item);
            }
        }
        return buyerCart;
    }

    public boolean set(String key, Serializable value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean set(String key, Serializable value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Serializable get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return (Serializable) result;
    }

    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    //正则表达式
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    public <HK, HV> boolean setMap(String key, Map<HK, HV> map) {
        boolean result = false;
        try {
            HashOperations<String, HK, HV> operations = redisTemplate.opsForHash();
            operations.putAll(key, map);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public <HK, HV> Map<HK, HV> getMap(String key) {
        HashOperations<String, HK, HV> operations = redisTemplate.opsForHash();
        return operations.entries(key);
    }


}
