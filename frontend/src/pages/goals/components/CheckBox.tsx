import { FormControlLabel, Checkbox } from "@mui/material";

interface CheckBoxProps {
  label: string;
  checked: boolean;
  onChange: () => void;
}

const CheckBox: React.FC<CheckBoxProps> = ({ label, checked, onChange }) => {
  return (
    <FormControlLabel
      control={
        <Checkbox checked={checked} onChange={onChange} color="primary" />
      }
      label={label}
    />
  );
};

export default CheckBox;
