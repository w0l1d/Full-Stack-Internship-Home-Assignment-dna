import {useState} from 'react';
import employeeService from '../services/EmployeeService';

const UploadForm = ({onUpload}) => {
    const [file, setFile] = useState(null);
    const [isUploading, setIsUploading] = useState(false);
    const [error, setError] = useState(null);

    const handleFileChange = (event) => {
        setFile(event.target.files?.[0]);
        setError(null); // Clear previous errors when a new file is selected
    };

    const handleUpload = async () => {
        if (!file) {
            setError('Please select a file.');
            return;
        }

        setIsUploading(true);
        try {
            const formData = new FormData();
            formData.append('file', file);
            const result = await employeeService.uploadCsv(formData);
            onUpload(result);
        } catch (error) {
            setError('An error occurred during the upload.');
        } finally {
            setIsUploading(false);
        }
    };

    return (
        <div className="my-4">
            <div className="mt-1 ">


                <label className="block mb-2 text-sm font-medium text-gray-900 " htmlFor="file_input">Upload
                    file</label>
                <div className={"flex "}>
                    <input
                        className="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none"
                        aria-describedby="file_input_help" id="file_input" onChange={handleFileChange} type="file"/>
                    {file && (
                        <button
                            onClick={handleUpload}
                            className="ml-2 bg-blue-500 text-white py-2 px-4 rounded"
                            disabled={isUploading}
                        >
                            {isUploading ? 'Uploading...' : 'Upload'}
                        </button>
                    )}
                </div>
                <p className="mt-1 text-sm text-gray-500 " id="file_input_help">CSV format only</p>


            </div>
            {error && <p className="text-red-500 text-sm mt-2">{error}</p>}
        </div>
    );
};

export default UploadForm;
