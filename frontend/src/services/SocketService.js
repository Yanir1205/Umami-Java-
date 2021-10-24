import io from 'socket.io-client';
import * as SockJS from 'sockjs-client';
var Stomp = require('stompjs');

const BASE_URL =
  // process.env.NODE_ENV === 'production' ? '/' : '//localhost:3030';
  process.env.NODE_ENV === 'production' ? '/' : '//localhost:8081';

let socket;

export default {
  setup,
  terminate,
  on,
  off,
  emit
};

function setup() {
  let sock = new SockJS("http://localhost:8081/stomp");
  socket = Stomp.over(sock);
}

function terminate() {
  socket.disconnect(() => {
    console.log(`Connected: ${socket.connected}`);
  })
}

function on(eventName, cb) {
  if(socket.connected){
    console.log("Subscribing");
    socket.subscribe(eventName, payload => {
      cb(payload);
    })
  } else{
    socket.connect({}, () => {
      console.log(`Connected: ${socket.connected}`);
      on(eventName, cb);
    })
  }
}

function off(eventName, cb) {
  // socket.off(eventName, cb);
}

function emit(eventName, data) { 
  if(socket.connected){
    console.log("Event emitted", JSON.parse(data));
    socket.send(eventName, {}, data);
  } 
  else {
    socket.connect({}, () => {
      emit(eventName, data);
    })
  }
}
