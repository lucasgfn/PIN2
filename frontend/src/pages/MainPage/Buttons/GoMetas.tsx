import { Link } from "react-router-dom";

export default function GoToMetas() {
  return (
    <div className="p-4">
      <Link
        to="/metas"
        className="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded transition duration-200"
      >
        Minhas Metas
      </Link>
    </div>
  );
}
