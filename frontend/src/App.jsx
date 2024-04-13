import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import axios from 'axios'

function App() {
  const [transactions,setTransactions] = useState([]);
  const [file,setFile] = useState(null);

  const fetchTransactions = async () => {
    const response =  await axios.get("http://localhost:8080/transacoes");
    setTransactions(response.data);
    console.log(response.data);

  }

  const headleFileChange= (e)=>{
    setFile(e.target.file[0]);
  }

  const uploadFile = async () =>{
    const formData = new FormData();
    formData.append('file', file);
    axios.post("http://localhost:8080/cnab/upload", formData,{
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  }

  useEffect(()=>{
    fetchTransactions();
  },[]);

  return (
    <div className="p-4">
       <h1 className="text-2xl font-semibold mb-4">Importação de CNAB</h1>
        <div className="mb-8">
        <div className="flex items-center space-x-4">
          <label className="text-gray-600">
            <span className="bg-blue-500 hover:bg-blue-600 cursor-pointer text-white py-2 px-4 rounded-lg">
              Choose File
            </span>
            <input
              type="file"
              className="hidden"
              onChange={headleFileChange}
              accept=".txt"
            />
          </label>
          <button
            className="bg-blue-700 hover:bg-blue-800 text-white py-2 px-4 rounded-lg"
            onClick={uploadFile}
          >
      
            Upload File
          </button>
        </div>
        </div>
     

      <div className="p-4">
        <h2 className="text-2xl font-semibold mb-4">Transações</h2>
        <ul className="bg-white shadow-md rounded-md p-4">
          {transactions.map((report, key)=>(
             <li className="mb-4 p-4 border-b border-gray-300 flex flex-col">
             <table className="table-auto w-full">
               <thead>
                 <tr>
                   <th className="px-4 py-2">Cartão</th>
                   <th className="px-4 py-2">CPF</th>
                   <th className="px-4 py-2">Data</th>
                   <th className="px-4 py-2">Dono da Loja</th>
                   <th className="px-4 py-2">Hora</th>
                   <th className="px-4 py-2">Nome da Loja</th>
                   <th className="px-4 py-2">Tipo</th>
                   <th className="px-4 py-2">Valor</th>
                 </tr>
               </thead>
               <body>
                 {report.transacoes.map((transacao, key)=>{
                    <tr>
                     <td className="px-4 py-2">{transacao.cartao}</td>
                         <td className="px-4 py-2">{transacao.cpf}</td>
                         <td className="px-4 py-2">{transacao.data}</td>
                         <td className="px-4 py-2">{transacao.donoDaLoja}</td>
                         <td className="px-4 py-2">{transacao.hora}</td>
                         <td className="px-4 py-2">{transacao.nomeDaLoja}</td>
                         <td className="px-4 py-2">{transacao.tipo}</td>
                     </tr>
                 })};
                
               </body>
             </table>
             </li>
          ))};
        
       </ul>
      </div>
    </div>
    
  );
}

export default App
