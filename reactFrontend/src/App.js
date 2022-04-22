import Header from './components/Header.js'
import Form from './components/Form.js'
import {ToastContainer} from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'



function App() {
  return (
    <div className='main'>
      <Header />
      <Form />
      <ToastContainer />
    </div>
  )
}

export default App;
