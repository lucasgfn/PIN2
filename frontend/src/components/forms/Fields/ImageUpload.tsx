import { useState } from "react";
import { Box, IconButton, Tooltip, Typography } from "@mui/material";
import UploadIcon from "@mui/icons-material/Upload";

interface ImageUploadProps {
  onImageSelect: (file: File | null) => void;
  initialImageUrl?: string | null;
}

const ImageUpload: React.FC<ImageUploadProps> = ({
  onImageSelect,
  initialImageUrl = null,
}) => {
  const [preview, setPreview] = useState<string | null>(initialImageUrl);


  const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files[0]) {
      const file = event.target.files[0];
      setPreview(URL.createObjectURL(file));
      onImageSelect(file);
    } else {
      setPreview(null);
      onImageSelect(null);
    }
  };

  return (
    <Box display="flex" flexDirection="column" alignItems="flex-start">
      <Box display="flex" alignItems="center">
        <Typography variant="body1" mr={1}>
          Imagem:
        </Typography>
        <input
          accept="image/*"
          style={{ display: "none" }}
          id="upload-image"
          type="file"
          onChange={handleImageChange}
        />
        <label htmlFor="upload-image">
          <Tooltip title="Selecionar imagem">
            <IconButton component="span" color="primary">
              <UploadIcon />
            </IconButton>
          </Tooltip>
        </label>
      </Box>

      {preview && (
        <Box mt={2}>
          <img
            src={preview}
            alt="Preview"
            style={{ maxWidth: "100%", maxHeight: 300 }}
          />
        </Box>
      )}
    </Box>
  );
};

export default ImageUpload;
