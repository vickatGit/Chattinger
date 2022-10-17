// const express = require('express');
// const bodyParser = require('body-parser');


// var app = express();
// const io = require('socket.io')(3000);

// // parse application/x-www-form-urlencoded
// // { extended: true } : support nested object
// // Returns middleware that ONLY parses url-encoded bodies and 
// // This object will contain key-value pairs, where the value can be a 
// // string or array(when extended is false), or any type (when extended is true)
// app.use(bodyParser.urlencoded({ extended: true }));

// //This return middleware that only parses json and only looks at requests where the Content-type
// //header matched the type option. 
// //When you use req.body -> this is using body-parser cause it is going to parse 
// // the request body to the form we want
// app.use(bodyParser.json());


// var server = app.listen(4000,()=>{
//     console.log('Server is running on port number 3000')
// })


const http = require('http');
const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const server = http.createServer(app);
app.use(bodyParser.json());
const io = require('socket.io')(server);
const PORT = 5000;

server.listen(PORT, () => {
console.log(`Server is Listening On Port ${PORT}`);
});





io.on('connection',function(socket) {

    var userName = '';    
    socket.on('subscribe', function(data) {
        console.log('subscribe trigged')
        const room_data = JSON.parse(data)
        userName = room_data.userName;
        const roomName = room_data.roomName;
    
        socket.join(`${roomName}`)
        console.log(`Username : ${userName} joined Room Name : ${roomName}`)

        // .to is equal to broadcast.emit
        io.to(`${roomName}`).emit('newUserToChatRoom',userName);

    })

    socket.on('unsubscribe',function(data) {
    
        const room_data = JSON.parse(data)
        const userName = room_data.userName;
        const roomName = room_data.roomName;

        socket.broadcast.to(`${roomName}`).emit('userLeftChatRoom',userName)
        socket.leave(`${roomName}`)
    })


    socket.on('newMessage',function(data) {
        console.log('newMessage triggered')

        const messageData = JSON.parse(data)
        const messageContent = messageData.messageContent
        const roomName = messageData.roomName


        const chatData = {
            userName : userName,
            messageContent : messageContent,
            roomName : roomName
        }
        socket.broadcast.to(`${roomName}`).emit('updateChat',JSON.stringify(chatData)) // Need to be parsed into Kotlin object in Kotlin
    })

})

module.exports = server; //Exporting for test