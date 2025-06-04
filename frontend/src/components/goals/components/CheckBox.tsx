import { FormGroup, FormControlLabel, Checkbox } from "@mui/material";

interface CheckBoxProps {
  label: string;
}

const CheckBox: React.FC<CheckBoxProps> = ({ label }) => {
  return (
    <FormGroup>
      <FormControlLabel control={<Checkbox />} label={label} />
    </FormGroup>
  );
};

export default CheckBox;
