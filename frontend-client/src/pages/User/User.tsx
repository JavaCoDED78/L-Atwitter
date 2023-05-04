import { useHomeStyles } from "../Home/HomeStyles";
import { Paper, Typography } from "@material-ui/core";
import BackButton from "../../components/BackButton/BackButton";

export const User = () => {
  const classes = useHomeStyles();

  return (
    <Paper className={classes.tweetsWrapper} variant="outlined">
      <Paper className={classes.tweetsHeader} variant="outlined">
        <BackButton />
        <div>
          <Typography variant="h6">Default</Typography>
          <Typography variant="caption" display="block" gutterBottom>
            65 twits
          </Typography>
        </div>
      </Paper>
    </Paper>
  );
};
