import {createStyles, makeStyles, OutlinedInputProps, TextField, TextFieldProps, Theme} from "@material-ui/core";

export const useChangeInfoTextFieldStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            width: "100%",
            height: 58,
            border: '1px solid #C4C4C4',
            overflow: 'hidden',
            borderRadius: 4,
            backgroundColor: '#fff',
            transition: theme.transitions.create(['border-color', 'box-shadow']),
            '&:hover': {
                backgroundColor: '#fff',
            },
            '&$focused': {
                backgroundColor: '#fff',
                borderWidth: 2,
                borderColor: theme.palette.primary.main,
            },
        },
        error: {
            border: '1px solid rgb(224, 36, 94)',
            backgroundColor: '#fff',
            '&:hover': {
                backgroundColor: '#fff',
            },
            '&$focused': {
                backgroundColor: '#fff',
                border: '2px solid rgb(224, 36, 94)',
            },
        },
        disabled: {
            backgroundColor: "rgb(247, 249, 249)",
            color: "#849099",
            '&:hover': {
                backgroundColor: "rgb(247, 249, 249)",
            },
        },
        focused: {},
    }),
);

export const ChangeInfoTextField = (props: TextFieldProps) => {
    const classes = useChangeInfoTextFieldStyles();

    return (
        <TextField
            InputProps={{ classes, disableUnderline: true } as Partial<OutlinedInputProps>}
            {...props}
        />
    );
}
