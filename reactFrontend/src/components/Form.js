import { useState, useEffect } from 'react'
import axios from 'axios'
import { toast } from 'react-toastify'

const Form = () => {
  const [firstName, setFirstName] = useState('')
  const [lastName, setLastName] = useState('')
  const [email, setEmail] = useState('')
  const [phone, setPhone] = useState('')
  const [supervisor, setSupervisor] = useState(null)
  const [useEmail, setUseEmail] = useState(true)
  const [usePhone, setUsePhone] = useState(false)
  const [supervisors, setSupervisors] = useState([])


  useEffect(() => {
    const fetchSupervisors = async () => {
      const response = await axios.get('/api/supervisors')
      setSupervisors(response.data)
      setSupervisor(response.data[0])
    }
    fetchSupervisors()
  }, [])



  const onFormSubmit = async (e) => {
    e.preventDefault()

    const regex = /^([+]?[\s0-9]+)?(\d{3}|[(]?[0-9]+[)])?([-]?[\s]?[0-9])+$/i;
    if(!regex.test(phone)) {
      toast.error('Please enter a valid phone number')
      console.log(phone)
    } else {

    const formBody = {
      firstName,
      lastName,
      email,
      phone,
      supervisor
    }
  

    const response = await axios.post('/api/submit', formBody)
    const formData = response.data
    console.log(formData)
  }

    setFirstName('')
    setLastName('')
    setEmail('')
    setPhone('')
  }


  return (
    <form className="form-container" onSubmit={(e) => onFormSubmit(e)}>
      <h1>Form</h1>
      <div className="form-control">
        <label htmlFor="first-name" className='name'>First Name</label>
        <input type="text" value={firstName} onChange={(e) => setFirstName(e.target.value)} required />
      </div>
      <div className="form-control">
        <label htmlFor="last-name" className='name'>Last Name</label>
        <input type="text" value={lastName} onChange={(e) => setLastName(e.target.value)} required />
      </div>
      <p>How would you prefer to be notified?</p>
      <div className="form-control">
        <label htmlFor="email-check">Email</label>
        <input type="checkbox" id='email-check' onChange={() => setUseEmail(!useEmail)} checked={useEmail} required={!useEmail && !usePhone} />
        <br />
        <input type="email" id="email" value={email} onChange={(e) => setEmail(e.target.value)} disabled={!useEmail} required={useEmail} />
      </div>
      <div className="form-control">
        <label htmlFor="phone-check">Phone Number</label>
        <input type="checkbox" id='phone-check' onChange={() => setUsePhone(!usePhone)} />
        <br />
        <input type="text" value={phone} onChange={(e) => setPhone(e.target.value)} disabled={!usePhone} required={usePhone} />
      </div>
      <div className="form-control">
        <label htmlFor="supervisor">Supervisor</label>
        <br />
        <select name="supervisor" id="supervisor" onChange={(e) => setSupervisor(e.target.value)}>
          {supervisors.map((supervisor, index) => (
            <option key={index} value={supervisor}>{supervisor}</option>
          ))}
        </select>
      </div>
      <button type="submit" >Submit</button>
    </form>
  )
}
export default Form