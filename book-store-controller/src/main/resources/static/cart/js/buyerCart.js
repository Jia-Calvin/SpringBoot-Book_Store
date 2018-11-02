$(document).ready(function () {
    getUserName();
    getBuyerCart();
});

function getBuyerCart() {
    $.ajax({
        url: "/shopping/getBuyerCart",
        type: "POST",
        success: function (data) {
            var productItem = "productItem";
            if (data.toString() !== "") {
                var productImg = "productImg";
                var productName = "productName";
                var productPrice = "productPrice";
                var productAmount = "productAmount";
                var productTotalPrice = "productTotalPrice";
                for (var i = 0; i < data.items.length; i++) {
                    document.getElementById(productItem + (i + 1)).hidden = false;
                    var item = data.items[i].product;
                    $("#" + productName + (i + 1)).html(item.name);
                    $("#" + productPrice + (i + 1)).html(item.price.toFixed(2));
                    $("#" + productAmount + (i + 1)).html(data.items[i].amount);
                    $("#" + productTotalPrice + (i + 1)).html((data.items[i].amount * item.price).toFixed(2));
                    document.getElementById(productImg + (i + 1)).src = "/home/img/" + item.name + ".png";
                    updateOrderPrice(data.items.length);
                }
                for (var j = data.items.length; j < 10; j++) {
                    document.getElementById(productItem + (j + 1)).hidden = true;
                }
            } else {
                updateOrderPrice(0);
                alert("购物车空空如也！");
            }
            getFeePrice();
            updateTotalOrderPrice();
        }
    });
}

function updateOrderPrice(buyerItemNum) {
    var totalPrice = Number(0);
    var productTotalPrice = "productTotalPrice";
    for (var i = 0; i < buyerItemNum; i++) {
        var buyerItemPrice = document.getElementById(productTotalPrice + (i + 1)).innerText;
        totalPrice = Number(totalPrice) + Number(buyerItemPrice);
    }
    $("#orderPrice").html(totalPrice.toFixed(2));
}

function getFeePrice() {
    var currentPrice = document.getElementById("orderPrice").innerText;
    if (currentPrice.toString() === "0.00") {
        $("#feePrice").html("+0.00");
    } else if (currentPrice > 100.00) {
        $("#feePrice").html("+0.00");
    } else {
        $("#feePrice").html("+5.00");
    }
}

function updateTotalOrderPrice() {
    var feePrice = document.getElementById("feePrice").innerText;
    var currentPrice = document.getElementById("orderPrice").innerText;
    $("#totalOrderPrice").html((Number(feePrice) + Number(currentPrice)).toFixed(2));
}

function changeAmount(isAdd, num) {
    var productName = document.getElementById("productName" + num).innerText.toString();
    var productTotalPrice = "productTotalPrice";
    var productPrice = "productPrice";
    var productAmount = "productAmount" + num.toString();
    var addPrice = document.getElementById(productPrice + num).innerText;
    var currentAmount = document.getElementById(productAmount).innerText;
    if (isAdd) {
        currentAmount = Number(currentAmount) + Number(1);
        $("#" + productAmount).html(currentAmount.toString());
        $("#" + productTotalPrice + num).html((Number(addPrice) +
            Number(document.getElementById(productTotalPrice + num).innerText)).toFixed(2));
        saveChange(productName, isAdd);
    } else {
        currentAmount = Number(currentAmount) - Number(1);
        $("#" + productAmount).html(currentAmount.toString());
        if (currentAmount < 1) {
            $("#" + productAmount).html(1);
        }
        else {
            $("#" + productTotalPrice + num).html((Number(document.getElementById(productTotalPrice + num).innerText)
                - Number(addPrice)).toFixed(2));
            saveChange(productName, isAdd);
        }
    }
    updateOrderPrice(10);
    getFeePrice();
    updateTotalOrderPrice();
}

function saveChange(productName, isAdd) {
    var amount;
    if (isAdd)
        amount = Number(1);
    else
        amount = Number(-1);
    $.ajax({
        url: "/shopping/buyerCart",
        type: "POST",
        data: {
            productName: productName,
            buyQuantity: amount
        }
    });
}

function deleteProduct(num) {
    var productName = document.getElementById("productName" + num).innerText;
    $.ajax({
        url: "/shopping/deleteProduct",
        type: "POST",
        data: {productName: productName},
        success: function () {
            window.location.href = "/shopping/toCart";
        }
    });
}

function clearBuyerCart() {
    $.ajax({
        url: "/shopping/clearBuyerCart",
        type: "POST",
        success: function () {
            window.location.href = "/cart/buyerCart.html";
        }
    });
}

function getUserName() {
    $.ajax({
        url: "/shopping/getUsername",
        type: "POST",
        success: function (data) {
            if (data !== "")
                $("#login").html("您好, " + data.toString()).removeAttr("href");
            else
                $("#login").html("请登录");
        }
    });
}
