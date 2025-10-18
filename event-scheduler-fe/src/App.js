import logo from './logo.svg';
import './App.css';
import Button from '@mui/material/Button';
import { useState } from 'react';


function App() {

  const [values, setValues] = useState({
    title:'',
    priority:'',
    speaker:'',
    vip:'',
    startdatetime:'',
    enddatetime:''
  }) 

  const handleChanges = (e) => {
    setValues({...values, [e.target.name] : [e.target.value]})
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(values);
  }

  const resetInput = () => {
    setValues({title:'',
    priority:'',
    speaker:'',
    vip:'',
    startdatetime:'',
    enddatetime:''})

  }
  return (
    <div className='container'>
      <h1>Event Scheduler</h1>
        <form onSubmit={handleSubmit}>
          <label htmlFor='title'>Title</label>
          <input type='text' name='title' placeholder='enter title' onChange={(e) => handleChanges(e)} required value={values.title}/>

          <label htmlFor='priority'>Priority</label>
          <input type='text' name='priority' placeholder='enter priority' onChange={(e) => handleChanges(e)} required  value={values.priority}/>

          <label htmlFor='speaker'>Speaker</label>
          <input type='text' name='speaker' placeholder='enter speaker' onChange={(e) => handleChanges(e)} required  value={values.speaker}/>

          <label htmlFor='vip'>VIP</label>
          <input type='text' name='vip' placeholder='enter vip' onChange={(e) => handleChanges(e)} required  value={values.vip}/>

          <label htmlFor='startdatetime'>Start Date/Time</label>
          <input type='text' name='startdatetime' placeholder='enter start date and time' onChange={(e) => handleChanges(e)} required  value={values.startdatetime}/>

           <label htmlFor='enddatetime'>End Date/Time</label>
          <input type='text' name='enddatetime' placeholder='enter end date and time' onChange={(e) => handleChanges(e)} required  value={values.enddatetime}/>

          <button type='button' onClick={resetInput}>Reset</button>
          <button type='submit'>Add Event</button>
        </form>
    </div>
    // <Button variant="contained" color="primary">
    //       Click Me
    //     </Button>
  );
}

export default App;
