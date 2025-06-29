import { Link } from "react-router-dom";

export default function GoToMetas() {
  return (
    <div className="p-4">
      <Link
        to="/metas"
        className="text-[#FF4081] font-semibold text-xl px-14 py-3 border-2 border-[#FF4081] rounded-[20px] transition duration-200 hover:bg-transparent hover:border-[#FF4081]"
      >
        Minhas Metas
      </Link>
    </div>
  );
}
