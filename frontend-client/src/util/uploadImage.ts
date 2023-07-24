import axios from "axios";
import {Image} from "../store/types/common";
import {API_USER_UPLOAD_IMAGE} from "./endpoints";

export const uploadImage = async (image: File): Promise<Image> => {
    const formData = new FormData();
    formData.append("file", image);

    const {data} = await axios.post(API_USER_UPLOAD_IMAGE, formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
    });

    return data;
};
