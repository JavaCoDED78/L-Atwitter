import React, { FC, ReactElement } from "react";
import { useHistory } from "react-router-dom";
import IconButton from "@material-ui/core/IconButton";
import { ArrowBack } from "@material-ui/icons";

const BackButton: FC = (): ReactElement => {
  const history = useHistory();

  const handleClickButton = () => {
    history.goBack();
  };

  return (
    <IconButton
      onClick={handleClickButton}
      style={{ marginRight: 20 }}
      color="primary"
    >
      <ArrowBack />
    </IconButton>
  );
};

export default BackButton;
