import {useState} from 'react';
import UploadForm from '../components/UploadForm';
import EmployeeTable from '../components/EmployeeTable';
import JobSummaryTable from '../components/JobSummaryTable';
import employeeService from '../services/EmployeeService';

const Home = () => {
    const [uploadedData, setUploadedData] = useState(null);
    const [jobSummary, setJobSummary] = useState(null);

    const handleUpload = async (data) => {
        setUploadedData(data);

        // Assuming you have a function to get job summary from the backend
        try {
            const summary = await employeeService.getSummary(data);
            setJobSummary(summary);
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <>        <UploadForm onUpload={handleUpload}/>

            {uploadedData && (
                <div>
                    <hr className="mt-10 mb-2 border-1"/>
                    <h2 className="text-xl font-bold mb-4 py-2">Employee Information</h2>
                    <EmployeeTable employees={uploadedData}/>

                    <hr className="mt-10 mb-2 border-1"/>
                    {jobSummary && (
                        <div>
                            <h2 className="text-xl font-bold mb-4 py-2">Jobs Summary</h2>
                            <JobSummaryTable jobSummary={jobSummary}/>
                        </div>
                    )}
                </div>
            )}
        </>

    );
};

export default Home;
