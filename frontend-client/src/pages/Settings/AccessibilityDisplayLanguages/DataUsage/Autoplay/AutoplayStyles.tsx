import { makeStyles, Theme } from "@material-ui/core";

export const useAutoplayStyles = makeStyles((theme: Theme) => ({
    infoItemWrapper: {
        padding: "12px 16px",
        "& .MuiTypography-h6, .MuiTypography-subtitle2": {
            marginBottom: 4
        }
    }
}));
