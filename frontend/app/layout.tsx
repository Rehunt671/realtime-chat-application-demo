"use client";
import React from "react";
import "./globals.css";
import { usePathname } from "next/navigation";
import Navbar from "@/components/Navbar";
import { Provider as ReduxProvider } from "react-redux";
import store from "@/stores/store";
interface RootLayoutProps {
  children: React.ReactNode;
}

const RootLayout: React.FC<RootLayoutProps> = ({ children }) => {
  const pathname = usePathname();
  const isJoinPageOrRootPage = pathname === "/join" || pathname === "/";

  return (
    <html lang="en">
      <body>
        <ReduxProvider store={store}>
          {!isJoinPageOrRootPage && <Navbar />}
          <main>{children}</main>
        </ReduxProvider>
      </body>
    </html>
  );
};

export default RootLayout;
