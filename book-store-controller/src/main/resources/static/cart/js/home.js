$(document).ready(function () {
    document.getElementById("bookDetail").hidden = true;
    getUserName();
    getProduct("计算机");
});

function getUserName() {
    $.ajax({
        url: "/shopping/getUsername",
        type: "POST",
        success: function (data) {
            if (data !== "") {
                $("#login").html("您好, " + data.toString()).removeAttr("href");
                document.getElementById("logout").hidden = false;
            }
            else
                $("#login").html("请登录");
        }
    });
}


function getProduct(cate) {
    var category = ["文学", "计算机", "外语", "学术", "励志", "艺术", "科技", "生活"];
    for (var k = 0; k < category.length; k++) {
        $("#" + category[k]).removeClass("yMenua");
    }
    $("#" + cate).addClass("yMenua");
    document.getElementById("bookDetail").hidden = true;
    $.ajax({
        url: "/shopping/getProductByCate",
        data: {category: cate},
        type: "POST",
        success: function (data) {
            $("#productSum").html(data.length);
            for (var i = 0; i < data.length; i++) {
                var indBook = "book";
                indBook = indBook + (i + 1).toString();
                document.getElementById(indBook).hidden = false;
                var indImg = "bookImg";
                var indName = "bookName";
                var indPrice = "bookPrice";
                indImg = indImg + (i + 1).toString();
                indName = indName + (i + 1).toString();
                indPrice = indPrice + (i + 1).toString();
                document.getElementById(indImg).src = "/home/img/" + data[i].name + ".png";
                $("#" + indName).html(data[i].name);
                $("#" + indPrice).html(data[i].price.toFixed(2));
            }
            for (i = data.length; i < 8; i++) {
                indBook = "book";
                indBook = indBook + (i + 1).toString();
                document.getElementById(indBook).hidden = true;
            }
            document.getElementById("jumpPage").hidden = false;
        }
    });
}

function showBookDetail(data) {
    document.getElementById("bookDetail").hidden = false;
    for (var i = 0; i < 8; i++) {
        var indBook = "book";
        indBook = indBook + (i + 1).toString();
        document.getElementById(indBook).hidden = true;
    }
    document.getElementById("jumpPage").hidden = true;

    var bookName = document.getElementById(data).innerText;
    $.ajax({
        url: "/shopping/getProductByName",
        data: {bookName: bookName},
        type: "POST",
        success: function (product) {
            $("#bookNameDetail").html(product.name);
            $("#bookPriceAddDetail").html("￥" + (product.price + 13.14).toFixed(2));
            document.getElementById("bookImgDetail").src = "/home/img/" + product.name + ".png";
            $("#bookDescriptionDetail").html(product.description);
            $("#bookQuantityDetail").html(product.quantity.toString());
            $("#bookPriceDetail").html("￥" + product.price.toFixed(2));
            var currentAuthors = "";
            for (var j = 0; j < product.authors.length; j++) {
                $("#bookAuthorDetail").html(currentAuthors + (product.authors)[j] + "; ");
                currentAuthors = document.getElementById("bookAuthorDetail").innerText;
            }
        }
    });
}

function changeQuantity(data) {
    var currentQuantity = parseInt(document.getElementById("inputQuantity").value);
    if (data) {
        currentQuantity = currentQuantity + 1;
        document.getElementById("inputQuantity").value = currentQuantity.toString();
    }
    else {
        currentQuantity = currentQuantity - 1;
        if (currentQuantity <= 0)
            currentQuantity = 0;
        document.getElementById("inputQuantity").value = currentQuantity.toString();
    }
}

function logout() {
    $.ajax({
        url: "/logout",
        type: "POST",
        success: function () {
            window.location.replace("/login");
        }
    });
}

function checkLogin(type) {
    var isLogin = false;
    $.ajax({
        url: "/shopping/getUsername",
        type: "POST",
        success: function (username) {
            if (username.toString() !== "") {
                isLogin = true;
            }
            if (!isLogin) {
                alert("请登录");
                window.location.replace("/login");
            }
            else if (type.toString() === "buy") {
                addToCart();
            }
            else if (type.toString() === "add") {
                addToCart();
            }
        }
    });
}

function addToCart() {
    var buyQuantity = parseInt(document.getElementById("inputQuantity").value);
    var buyUpperBound = parseInt(document.getElementById("bookQuantityDetail").innerText);
    if (buyQuantity > buyUpperBound)
        alert("加入购物车数量超出库存数量！");
    else {
        var productName = document.getElementById("bookNameDetail").innerText;
        $.ajax({
            url: "/shopping/buyerCart",
            type: "POST",
            data: {
                productName: productName,
                buyQuantity: buyQuantity
            },
            success: function () {
                window.location.href = "/shopping/toCart";
            }
        });
    }
}


