import { useDispatch } from "react-redux";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import {
  selectWebsocket,
  setConnectionStatus,
  setWebSocketClient,
} from "@/stores/slices/webSocketSlice";
import { useAppSelector } from "@/stores/hook";
import { addMessageToRoom } from "@/stores/slices/roomSlice";

export const useWebSocket = () => {
  const dispatch = useDispatch();
  const { client, isConnected } = useAppSelector(selectWebsocket);
  const serverUrl = process.env.API_BASE_URL;

  const sendMessage = (destination: string, message: any) => {
    if (client && isConnected) {
      console.log("send", JSON.stringify(message));
      client.send(`/app${destination}`, {}, JSON.stringify(message));
    }
  };

  const connect = (username: string) => {
    try {
      const socket = new SockJS(`${serverUrl}/ws`);
      const stompClient = Stomp.over(socket);
      stompClient.connect({}, () => onConnected(stompClient, username));
      stompClient.debug = () => {};
    } catch (e) {
      console.log(e);
    }
  };

  const disconnect = () => {
    if (client && isConnected) {
      client.disconnect(() => {
        console.log("WebSocket disconnected");
        dispatch(setConnectionStatus(false));
        dispatch(setWebSocketClient(null));
      });
    } else {
      console.log("No active WebSocket connection to disconnect.");
    }
  };

  const onConnected = (stompClient: Stomp.Client, username: string) => {
    console.log("websocket connected successfuly");
    stompClient.subscribe(`/topic/room`, onUpdateRoom);
    sendMessage(`/chat/addUser`, {
      sender: username,
      message: `${username} has joined the chat`,
      type: "JOIN",
    });
    dispatch(setWebSocketClient(stompClient));
    dispatch(setConnectionStatus(true));
  };

  const onUpdateRoom = (payload: Stomp.Message) => {
    const messageObject = JSON.parse(payload.body);
    console.log("messageObject", messageObject);
    dispatch(addMessageToRoom(messageObject));
  };

  return {
    connect,
    disconnect,
    isConnected,
    sendMessage,
  };
};
