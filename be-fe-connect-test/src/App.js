import logo from './logo.svg';
import './App.css';
import Home from "./pages/Home";
import Form from './pages/Form';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";

function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Home/>}></Route>
        <Route path='/form' element={<Form/>}></Route>
      </Routes>
    </Router>
  );
}

export default App;
