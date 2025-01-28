"use client";

import { useEffect } from "react";
import { useWebSocket } from "@/hooks/useWebsocket";
import { useAppSelector } from "@/stores/hook";
import { selectUser } from "@/stores/slices/userSlice";

export const WebSocketProvider = ({ children }: { children: React.ReactNode }) => {
  const { connect } = useWebSocket();
  useEffect(() => {
    connect();
  }, []);

  return <>{children}</>;
};

export default WebSocketProvider;
