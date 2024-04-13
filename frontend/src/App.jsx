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

  const uploadFile = async (e) =>{
    const formData = new FormData();
    formData.append('file', file);
    axios.post("http://localhost:8080/cnab/upload", formData,{
      headers: {
        'Content-type': 'multipart/form-data'
      }
    });
  }

  useEffect(()=>{
    fetchTransactions();
  },[]);
  return (
    <div>
      <div>
        <h1>Importacao de CNAB</h1>
      </div>
    
    <div>
      <span>Cloose File</span>
      <input type="file" 
      accept='.text' 
        onChange={headleFileChange}
      />
      <button onClick={uploadFile}>Update File</button>
    </div>
      <div>
        <h2>Transacoes</h2>
        <ul>
          <thead>
            <tr>
              <th className="">Cartao</th>
              <th className="">CPF</th>
              <th className="">Data</th>
              <th className="">Dono da Loja</th>
              <th className="">Hora</th>
              <th className="">Nome da Loja</th>
              <th className="">Tipo</th>
              <th className="">Valor</th>
            </tr>

          </thead>
          <body>
            <tr>
              <td>transacao.cartao</td>
              <td>transacao.cpf</td>
              <td>transacao.data</td>
              <td>transacao.donoDaLoja</td>
              <td>transacao.hora</td>
              <td>transacao.nomeDaLoja</td>
              <td>transacao.tipo</td>
              <td>transacao.valor</td>
            </tr>
          </body>
        </ul>

      </div>
    </div>
  )
}

export default App
