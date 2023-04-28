import {useHomeStyles} from "../../pages/Home/HomeStyles";
import React from "react";
import {Avatar, Button, CircularProgress, TextareaAutosize} from "@material-ui/core";
import IconButton from "@material-ui/core/IconButton";
import {ImageOutlined, SentimentSatisfiedOutlined} from "@material-ui/icons";
import classNames from "classnames";

interface AddTweetFormProps {
    classes: ReturnType<typeof useHomeStyles>;
    maxRows?: number;
}

const MAX_LENGTH = 320;

export const AddTweetForm: React.FC<AddTweetFormProps> = ({
                                                              classes,
                                                              maxRows,
                                                          }: AddTweetFormProps): React.ReactElement => {
    const [text, setText] = React.useState<string>('');
    const textLimitPercent = Math.round((text.length / 320) * 100);
    const textCount = MAX_LENGTH - text.length;

    const handleChangeTextarea = (e: React.FormEvent<HTMLTextAreaElement>): void => {
        if (e.currentTarget) {
            setText(e.currentTarget.value);
        }
    };

    const handleClickAddTweet = (): void => {
        setText('');
    };

    return (
        <div>
            <div className={classes.addFormBody}>
                <Avatar
                    className={classes.tweetAvatar}
                    alt={"Avatar User"}
                    src="https://cs8.pikabu.ru/post_img/2016/05/27/5/1464332742115529028.jpg"
                />
                <TextareaAutosize
                    onChange={handleChangeTextarea}
                    className={classes.addFormTextarea}
                    placeholder="Hello world!"
                    value={text}
                    maxRows={maxRows}
                />
            </div>
            <div className={classes.addFormBottom}>
                <div className={classNames(classes.tweetFooter, classes.addFormBottomActions)}>
                    <IconButton color="primary">
                        <ImageOutlined style={{fontSize: 26}}/>
                    </IconButton>
                    <IconButton color="primary">
                        <SentimentSatisfiedOutlined style={{fontSize: 26}}/>
                    </IconButton>
                </div>
                <div className={classes.addFormBottomRight}>
                    {text && (
                        <>
                            <span>{textCount}</span>
                            <div className={classes.addFormCircleProgress}>
                                <CircularProgress
                                    variant="static"
                                    size={25}
                                    thickness={5}
                                    value={text.length >= MAX_LENGTH ? 100 : textLimitPercent}
                                    style={text.length >= MAX_LENGTH ? {color: 'red'} : undefined}
                                />
                                <CircularProgress
                                    style={{color: 'rgba(0, 0, 0, 0.1)'}}
                                    variant="static"
                                    size={25}
                                    thickness={5}
                                    value={100}
                                />
                            </div>
                        </>
                    )}
                    <Button
                        onClick={handleClickAddTweet}
                        disabled={text.length >= MAX_LENGTH}
                        color="primary"
                        variant="contained">
                        Tweet
                    </Button>
                </div>
            </div>
        </div>
    );
};
