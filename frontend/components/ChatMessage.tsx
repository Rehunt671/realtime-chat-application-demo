import React from "react";
import { FormatChatDate } from "@/utils/formatDateTime";
import { useAppSelector } from "@/stores/hook";
import { selectUser } from "@/stores/slices/userSlice";
import { ChatMessage } from "@/types/chat_message";

interface ChatMessageProps {
  msg: ChatMessage;
}

const ChatMessageCard: React.FC<ChatMessageProps> = ({ msg }) => {
  const user = useAppSelector(selectUser);

  const renderMessageContent = () => {
    switch (msg.type) {
      case "CREATE":
        return (
          <span className="text-blue-600 font-semibold text-center">
            {msg.text}
          </span>
        );
      case "ENTER":
        return <span className="text-green-600 text-center">{msg.text}</span>;
      case "JOIN":
        return (
          <span className="text-indigo-600 text-center">
            {msg.sender + " has joined the chat"}.
          </span>
        );
      case "CHAT":
        return <span className="text-gray-800">{msg.text}</span>;
      case "LEAVE":
        return (
          <span className="text-red-600 text-center">
            {msg.sender + " has leaved the chat"}
          </span>
        );
      case "EXIT":
        return <span className="text-yellow-600">{msg.text}</span>;
      default:
        return null;
    }
  };

  const isMessageFromCurrentUser = msg.sender === user?.username;
  const isChatMessage = ["CHAT"].includes(msg.type);

  const itemAlignment = !isChatMessage
    ? "justify-center"
    : isMessageFromCurrentUser
    ? "justify-end"
    : "justify-start";

  return (
    <div
      key={msg.id}
      className={`${itemAlignment} flex items-start space-x-3 p-2`}
    >
      {isChatMessage && (
        <div className="flex flex-col justify-center">
          <div className="h-12 w-12 flex items-center justify-center rounded-full bg-gradient-to-br from-blue-500 to-blue-700 text-white font-semibold uppercase shadow-md border-2 border-white">
            {msg.sender.charAt(0)}
          </div>
        </div>
      )}
      <div className="max-w-sm bg-white px-4 py-3 rounded-xl shadow-lg border border-gray-200">
        <p className="text-gray-800 leading-relaxed break-words whitespace-pre-wrap">
          {renderMessageContent()}
        </p>
        {isChatMessage && (
          <span className="block mt-2 text-xs text-gray-500 text-right">
            {FormatChatDate(msg.datetime)}
          </span>
        )}
      </div>
    </div>
  );
};

export default ChatMessageCard;
