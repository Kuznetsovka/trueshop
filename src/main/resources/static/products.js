
var stomp = null;

// подключаемся к серверу по окончании загрузки страницы
window.onload = function() {
    connect();
};

function connect() {
    var socket = new SockJS('/socket');
    stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stomp.subscribe('/topic/product', function (product) {
            renderItem(product);
        });
    });
}

// хук на интерфейс
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendContent(); });
});
//Хотел чтобы ланные обновляли страницу product но не выходит, дописывает данные на той-же странице.
// отправка сообщения на сервер
function sendContent() {
    stomp.send("/app/product", {}, JSON.stringify({
        'title': $("#title").val(),
        'price': $("#price").val()
    }));
}

// рендер сообщения, полученного от сервера
function renderItem(productJson) {
    var product = JSON.parse(productJson.body);
    $("#table").append("<tr>" +
        "<td>" +product.title +"</td>" +
        "<td>" +product.price +"</td>" +
     "</tr>");
//Жаль нельза ID достать
//    $("#table").append("<tr>" +
//        "<td><a href='/products/" + product.id +"text=" + product.title + "</td>" +
//        "<td>" + product.price +"</td>" +
//        "<td>"
//            "<form action='#' action='/products/bucket/add?id=" + product.id + " method='post'>" +
//                "<button type='submit'>Add</button>" +
//            "</form>" +
//        "</td>" +
//        "<td>"
//            "<form action='#' action='/products/delete?id=" + product.id " method='post'>" +
//                "<button type='submit'>Delete</button>" +
//            "</form>" +
//        "</td>" +
//    "</tr>");
}
