import { ChatMessage, ChatMessageRequest } from "./contracts/state";
import {
  AddChatMessageActionInterface,
  ChatMessagesActionsType,
  FetchChatMessagesActionInterface,
  SetChatMessageActionInterface,
  SetChatMessagesActionInterface,
  SetChatMessagesLoadingStateActionInterface,
} from "./contracts/actionTypes";
import { LoadingStatus } from "../../types";

export const setChatMessages = (
  payload: ChatMessage[]
): SetChatMessagesActionInterface => ({
  type: ChatMessagesActionsType.SET_CHAT_MESSAGES,
  payload,
});

export const setChatMessage = (
  payload: ChatMessage
): SetChatMessageActionInterface => ({
  type: ChatMessagesActionsType.SET_CHAT_MESSAGE,
  payload,
});

export const addChatMessage = (
  payload: ChatMessageRequest
): AddChatMessageActionInterface => ({
  type: ChatMessagesActionsType.ADD_CHAT_MESSAGE,
  payload,
});

export const fetchChatMessages = (
  payload: number
): FetchChatMessagesActionInterface => ({
  type: ChatMessagesActionsType.FETCH_CHAT_MESSAGES,
  payload,
});

export const setChatMessagesLoadingState = (
  payload: LoadingStatus
): SetChatMessagesLoadingStateActionInterface => ({
  type: ChatMessagesActionsType.SET_LOADING_STATE,
  payload,
});