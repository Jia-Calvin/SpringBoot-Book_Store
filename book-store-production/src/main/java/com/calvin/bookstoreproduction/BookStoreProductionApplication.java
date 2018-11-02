package com.calvin.bookstoreproduction;

import com.calvin.bookstoreproduction.Service.ProductionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BookStoreProductionApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(BookStoreProductionApplication
                .class, args);

        ProductionService productionService = applicationContext.getBean(ProductionService.class);
//        List<Product> products = new ArrayList<>();
//        products.add(new Product("Java Web高级编程", new ArrayList<String>(Arrays.asList("Nicholas S. Williams")),
//                99.80F, "开发者可以通过阅读本书拓展自己的Java方面的知识，提升自己的技能。架构师通过阅读本书" +
//                "可以在团队项目中应用本书详细讨论的一些Web软件开发概念和模式。", 100,
//                new ArrayList<String>(Arrays.asList("计算机", "学术", "外语", "经管"))));
//
//        products.add(new Product("Java核心技术卷 I", new ArrayList<String>(Arrays.asList("Cay S. Horstmann")),
//                119.00F, "本书仍然将读者群定位在那些打算将Java应用到实际工程项目中的程序涉及人员。本书假设读者" +
//                "是一名具有程序设计语言坚实背景知识的程序涉及人员。", 100,
//                new ArrayList<String>(Arrays.asList("计算机", "励志", "少儿", "科技"))));
//
//        products.add(new Product("Java核心技术卷 II", new ArrayList<String>(Arrays.asList("Cay S. Horstmann")),
//                139.00F, "本书是核心卷I的补充拓展，本书中大部分章节都是独立的，你可以研究自己最感兴趣的主题。", 100,
//                new ArrayList<String>(Arrays.asList("计算机", "科技", "学术", "文学"))));
//
//        products.add(new Product("深入分析Java Web", new ArrayList<String>(Arrays.asList("许令波")),
//                73.70F, "本书的内容设计从HTTP、Servlet、模板渲染、数据层、容器到JVM等Java Web开发的各个方面。", 100,
//                new ArrayList<String>(Arrays.asList("计算机", "科技", "学术", "生活"))));
//
//        products.add(new Product("jQuery基础教程", new ArrayList<String>(Arrays.asList("Jonathan Chaffer", "Karl " +
//                "Swedberg")),
//                45.00F, "本书以通俗易解的方式介绍了jQuery的基本概念，通过学习本书，即使曾经因编写JavaScript而受过挫折的人，" +
//                "也能够掌握为网页添加交互和动态效果的技术。", 100,
//                new ArrayList<String>(Arrays.asList("计算机", "艺术", "原版", "考试"))));
//
//        products.add(new Product("深入理解Java虚拟机", new ArrayList<String>(Arrays.asList("周志明")),
//                61.30F, "通过本书的学习，读者可以以一种相对轻松的方式学习虚拟机的运作原理，对Java程序员的成长也有较大的帮助。", 100,
//                new ArrayList<String>(Arrays.asList("计算机", "少儿", "原版", "文学"))));
//
//        products.add(new Product("Spring Boot实战", new ArrayList<String>(Arrays.asList("Craig Walls")),
//                46.60F, "Spring Boot旨在简化Spring的开发，Spring Boot设计了Spring的方方面面", 100,
//                new ArrayList<String>(Arrays.asList("计算机", "艺术", "科技", "学术"))));
//
//        products.add(new Product("Spring微服务实战", new ArrayList<String>(Arrays.asList("Craig Walls")),
//                62.30F, "Spring微服务给架构师提供了非常清晰的思路。", 100,
//                new ArrayList<String>(Arrays.asList("计算机", "艺术", "科技", "学术"))));
//
//        products.add(new Product("Spring实战", new ArrayList<String>(Arrays.asList("Craig Walls")),
//                70.20F, "Spring实战主要讲解Spring开发的任何用途上，Spring for everything。", 100,
//                new ArrayList<String>(Arrays.asList("计算机", "艺术", "科技", "学术"))));
//
//        for (Product product : products) {
//            productionService.addBook(product);
//        }

        System.out.println(productionService.getAllBook());
    }
}
