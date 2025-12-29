package com.myapp.model;

public class Message {


        private final String text;
        private final boolean fromMe;

        public Message(String text, boolean fromMe) {
            this.text = text;
            this.fromMe = fromMe;
        }

        public String getText() {
            return text;
        }

        public boolean isFromMe() {
            return fromMe;
        }

    }


