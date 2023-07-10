import React, { ReactNodeArray } from "react";
import reactStringReplace from "react-string-replace";
import { Emoji } from "emoji-mart";
import "emoji-mart/css/emoji-mart.css";

export const textFormatter = (text: string): ReactNodeArray => {
  let replacedText: ReactNodeArray;

  replacedText = reactStringReplace(text, /(\n)/g, (match, i) => (
    <>
      {match}
      <br />
    </>
  ));

  replacedText = reactStringReplace(replacedText, /(#\w+)\b/gi, (match, i) => (
    <b key={i} id="hashtag">
      {match}
    </b>
  ));

  replacedText = reactStringReplace(replacedText, /:(.+?):/g, (match, i) => (
    <Emoji native={false} key={i} emoji={match} set={"twitter"} size={20} />
  ));

  return replacedText;
};
