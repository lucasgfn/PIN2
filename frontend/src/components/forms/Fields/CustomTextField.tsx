import { TextField, type TextFieldProps } from "@mui/material";

const CustomTextField = (props: TextFieldProps) => {
  return (
    <TextField
      fullWidth
      margin="normal"
      required
      {...props}
      sx={{
        ...props.sx,
        "& .MuiOutlinedInput-root": {
          "& fieldset": {
            height : 52,
            borderColor: "#623333",
            borderRadius: 25,
          },
        },
      }}
    />
  );
};

export default CustomTextField;
