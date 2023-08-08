import React, { FC, ReactElement, useEffect, useState } from "react";
import { Button, Dialog, DialogContent, Radio, Typography } from "@material-ui/core";
import { CheckCircle, RadioButtonUnchecked } from "@material-ui/icons";
import { useDispatch } from "react-redux";

import { useDisplayModalStyles } from "./DisplayModalStyles";
import { CheckIcon, TweetIcon, VerifiedIcon } from "../../../../icons";
import { DisplayProps } from "../../../../pages/Settings/AccessibilityDisplayLanguages/Display/Display";
import { updateBackgroundColor, updateColorScheme } from "../../../../store/ducks/user/actionCreators";
import { BackgroundTheme, ColorScheme } from "../../../../types/common";
import { BACKGROUND, COLOR } from "../../../../constants/common-constants";

interface DisplayModalProps {
    visible?: boolean;
    onClose: () => void;
}

const DisplayModal: FC<DisplayModalProps & DisplayProps> = (
    {
        visible,
        onClose,
        changeBackgroundColor,
        changeColorScheme
    }
): ReactElement | null => {
    const classes = useDisplayModalStyles();
    const dispatch = useDispatch();
    const [selectedBackgroundColor, setSelectedBackgroundColor] = useState<BackgroundTheme>(BackgroundTheme.DEFAULT);
    const [selectedColor, setSelectedColor] = useState<ColorScheme>(ColorScheme.BLUE);

    useEffect(() => {
        if (visible) {
            const background = localStorage.getItem(BACKGROUND);
            const color = localStorage.getItem(COLOR);
            setSelectedBackgroundColor((background !== null) ? background as BackgroundTheme : BackgroundTheme.DEFAULT);
            setSelectedColor((color !== null) ? color as ColorScheme : ColorScheme.BLUE);
        }
    }, [visible]);

    const handleChangeBackgroundColor = (event: React.ChangeEvent<HTMLInputElement>): void => {
        processBackgroundColor(event.target.value as BackgroundTheme);
    };

    const onClickBackgroundColor = (background: BackgroundTheme): void => {
        processBackgroundColor(background);
    };

    const processBackgroundColor = (background: BackgroundTheme): void => {
        dispatch(updateBackgroundColor({ backgroundColor: background }));
        setSelectedBackgroundColor(background);
        changeBackgroundColor(background);
    };

    const onClickColor = (color: ColorScheme): void => {
        dispatch(updateColorScheme({ colorScheme: color }));
        setSelectedColor(color);
        changeColorScheme(color);
    };

    const ColorSelector: FC<{ color: ColorScheme }> = ({ color }): JSX.Element => {
        return (
            <div
                id={color.toLowerCase()}
                className={classes.colorItem}
                onClick={() => onClickColor(color)}
            >
                {(color === selectedColor) && (
                    <span className={classes.checkIcon}>
                        {CheckIcon}
                    </span>
                )}
            </div>
        );
    };

    if (!visible) {
        return null;
    }

    return (
        <Dialog open={visible} onClose={onClose}>
            <Typography variant={"h3"} component={"div"} className={classes.title}>
                Customize your view
            </Typography>
            <DialogContent className={classes.content}>
                <Typography variant={"subtitle1"} component={"div"} className={classes.text}>
                    Manage your font size, color, and background. These settings affect all the Twitter accounts on this
                    browser.
                </Typography>
                <div className={classes.tweetInfoWrapper}>
                    <div>
                        <div className={classes.tweetIconWrapper}>
                            <span className={classes.tweetIcon}>
                                {TweetIcon}
                            </span>
                        </div>
                    </div>
                    <div>
                        <div>
                            <Typography variant={"h6"} component={"span"} className={classes.tweetTitle}>
                                Twitter
                            </Typography>
                            <span className={classes.tweetVerifiedIcon}>
                                {VerifiedIcon}
                            </span>
                            <Typography variant={"subtitle1"} component={"span"}>
                                @Twitter
                            </Typography>
                            <Typography variant={"subtitle1"} component={"span"}>
                                {" · 31m"}
                            </Typography>
                        </div>
                        <Typography variant={"body1"} component={"div"} className={classes.tweetText}>
                            At the heart of Twitter are short messages called Tweets — just like this one — which can
                            include photos, videos, links, text, hashtags, and mentions like <span
                            className={classes.tweetLink}>@Twitter</span>
                        </Typography>
                    </div>
                </div>
                <Typography variant={"subtitle2"} component={"div"} className={classes.subtitle}>
                    Font size
                </Typography>
                <div className={classes.stepperWrapper}>
                    <Typography variant={"body1"} id={"xs"} component={"span"} className={classes.tweetText}>
                        Aa
                    </Typography>
                    <span className={classes.stepper}>
                        <div id={"xs"} className={classes.stepperPoint} />
                        <div id={"sm"} className={classes.stepperPoint} />
                        <div id={"md"} className={classes.stepperPoint} />
                        <div id={"lg"} className={classes.stepperPoint} />
                        <div id={"xl"} className={classes.stepperPoint} />
                    </span>
                    <Typography variant={"body1"} id={"xl"} component={"span"} className={classes.tweetText}>
                        Aa
                    </Typography>
                </div>
                <Typography variant={"subtitle2"} component={"div"} className={classes.subtitle}>
                    Color
                </Typography>
                <div className={classes.colorWrapper}>
                    <ColorSelector color={ColorScheme.BLUE} />
                    <ColorSelector color={ColorScheme.YELLOW} />
                    <ColorSelector color={ColorScheme.CRIMSON} />
                    <ColorSelector color={ColorScheme.VIOLET} />
                    <ColorSelector color={ColorScheme.ORANGE} />
                    <ColorSelector color={ColorScheme.GREEN} />
                </div>
                <Typography variant={"subtitle2"} component={"div"} className={classes.subtitle}>
                    Background
                </Typography>
                <div className={classes.backgroundContainer}>
                    <div className={classes.backgroundWrapper}>
                        <div id={"default"} className={classes.backgroundItem}
                             onClick={() => onClickBackgroundColor(BackgroundTheme.DEFAULT)}
                        >
                            <div className={classes.backgroundItemWrapper}>
                                <Radio
                                    checked={selectedBackgroundColor === BackgroundTheme.DEFAULT}
                                    onChange={handleChangeBackgroundColor}
                                    value={BackgroundTheme.DEFAULT}
                                    name="radio-buttons"
                                    inputProps={{ "aria-label": "Default" }}
                                    icon={<RadioButtonUnchecked color={"primary"} />}
                                    checkedIcon={<CheckCircle color={"primary"} />}
                                    size="small"
                                />
                            </div>
                            <Typography variant={"h6"} component={"span"}>
                                Default
                            </Typography>
                        </div>
                    </div>
                    <div className={classes.backgroundWrapper}>
                        <div id={"dim"} className={classes.backgroundItem}
                             onClick={() => onClickBackgroundColor(BackgroundTheme.DIM)}
                        >
                            <div className={classes.backgroundItemWrapper}>
                                <Radio
                                    checked={selectedBackgroundColor === BackgroundTheme.DIM}
                                    onChange={handleChangeBackgroundColor}
                                    value={BackgroundTheme.DIM}
                                    name="radio-buttons"
                                    inputProps={{ "aria-label": "Dim" }}
                                    icon={<RadioButtonUnchecked color={"primary"} />}
                                    checkedIcon={<CheckCircle color={"primary"} />}
                                    size="small"
                                />
                            </div>
                            <Typography variant={"h6"} component={"span"}>
                                Dim
                            </Typography>
                        </div>
                    </div>
                    <div className={classes.backgroundWrapper}>
                        <div id={"lights-out"} className={classes.backgroundItem}
                             onClick={() => onClickBackgroundColor(BackgroundTheme.LIGHTS_OUT)}
                        >
                            <div className={classes.backgroundItemWrapper}>
                                <Radio
                                    checked={selectedBackgroundColor === BackgroundTheme.LIGHTS_OUT}
                                    onChange={handleChangeBackgroundColor}
                                    value={BackgroundTheme.LIGHTS_OUT}
                                    name="radio-buttons"
                                    inputProps={{ "aria-label": "Lights-out" }}
                                    icon={<RadioButtonUnchecked color={"primary"} />}
                                    checkedIcon={<CheckCircle color={"primary"} />}
                                    size="small"
                                />
                            </div>
                            <Typography variant={"h6"} component={"span"}>
                                Lights out
                            </Typography>
                        </div>
                    </div>
                </div>
                <div className={classes.buttonWrapper}>
                    <Button
                        onClick={onClose}
                        variant="contained"
                        color="primary"
                        size="small"
                    >
                        Done
                    </Button>
                </div>
            </DialogContent>
        </Dialog>
    );
};

export default DisplayModal;
