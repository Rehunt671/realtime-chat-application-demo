export interface ChatMessage {
  type: "JOIN" | "CHAT" | "LEAVE";
  id: number;
  text: string;
  sender: string;
  datetime: Date;
}
