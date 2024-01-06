import { useState } from 'react';
import PropTypes from 'prop-types';

const JobSummaryTable = ({ jobSummary }) => {
    const rowsPerPage = 10;
    const [currentPage, setCurrentPage] = useState(1);

    const entries = Object.entries(jobSummary);
    const startIndex = (currentPage - 1) * rowsPerPage;
    const endIndex = startIndex + rowsPerPage;

    const totalPages = Math.ceil(entries.length / rowsPerPage);

    const handleChangePage = (newPage) => {
        setCurrentPage(newPage);
    };

    return (
        <div className="flex flex-col">
            <table className="table-auto mt-4">
                <thead>
                <tr>
                    <th className="px-4 py-2">Job Title</th>
                    <th className="px-4 py-2">Average Salary</th>
                </tr>
                </thead>
                <tbody>
                {entries.slice(startIndex, endIndex).map(([jobTitle, averageSalary], index) => (
                    <tr key={jobTitle} className={(index % 2 === 0 ? 'bg-gray-100' : 'bg-white')}>
                        <td className="px-4 py-2">{jobTitle}</td>
                        <td className="px-4 py-2">{averageSalary} DH</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <div className="mt-4 flex justify-end">
                <button
                    className={`bg-blue-500 text-white p-2 mr-2 ${currentPage === 1 ? 'opacity-50' : ''}`}
                    disabled={currentPage === 1}
                    onClick={() => handleChangePage(currentPage - 1)}
                >
                    Previous
                </button>
                <span className="px-2">
          Page {currentPage} of {totalPages}
        </span>
                <button
                    className={`bg-blue-500 text-white p-2 ml-2 ${currentPage === totalPages ? 'opacity-50' : ''}`}
                    disabled={currentPage === totalPages}
                    onClick={() => handleChangePage(currentPage + 1)}
                >
                    Next
                </button>
            </div>
        </div>
    );
};

JobSummaryTable.propTypes = {
    jobSummary: PropTypes.objectOf(
        PropTypes.shape({
            averageSalary: PropTypes.number.isRequired,
        })
    ).isRequired,
};

export default JobSummaryTable;
