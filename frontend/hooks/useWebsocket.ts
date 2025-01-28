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

  const subscribe = (destination: string, callback: (payload: Stomp.Message) => void) => { 
    if (client && isConnected) {
      client.subscribe(destination, callback);
    }
  }

  const sendMessage = (destination: string, message: any) => {
    if (client && isConnected) {
      console.log("send", JSON.stringify(message));
      client.send(`/app${destination}`, {}, JSON.stringify(message));
    }
  };

  const connect = () => {
    try {
      const socket = new SockJS(`${serverUrl}/ws`);
      const stompClient = Stomp.over(socket);
      stompClient.connect({}, () => onConnected(stompClient));
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

  const onConnected = (stompClient: Stomp.Client) => {
    console.log("websocket connected successfuly"); 
    stompClient.subscribe(`/topic/room`, onUpdateRoom);
    dispatch(setWebSocketClient(stompClient));
    dispatch(setConnectionStatus(true));
  };

  const onUpdateRoom = (payload: Stomp.Message) => {
    const newMessageObject = JSON.parse(payload.body);
    console.log("newMessageObject", newMessageObject);
    dispatch(addMessageToRoom(newMessageObject));
  };

  return {
    connect,
    disconnect,
    isConnected,
    sendMessage,
    subscribe,
  };
};
