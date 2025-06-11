import type { IUserData } from "../../interface/IUserData";

import gifCat from "../../assets/cat/catGif.gif"; // imagem default caso não tenha img
import { Avatar } from "@mui/material";


const PhotoComprador : React.FC<{user_id?: IUserData}> = ({user_id}) => {

   
    return(
        <Avatar
        alt={user_id?.nome}
        src={user_id?.img || gifCat}
        sx={{ width: 80, height: 80, cursor: "pointer", mb: 2 }}
      />
    )
}

export default PhotoComprador;