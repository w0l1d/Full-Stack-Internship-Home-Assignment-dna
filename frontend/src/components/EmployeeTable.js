import { useState } from 'react';
import PropTypes from 'prop-types';

const EmployeeTable = ({ employees }) => {
    const rowsPerPage = 10;
    const [currentPage, setCurrentPage] = useState(1);

    const startIndex = (currentPage - 1) * rowsPerPage;
    const endIndex = startIndex + rowsPerPage;

    const totalPages = Math.ceil(employees.length / rowsPerPage);

    const handleChangePage = (newPage) => {
        setCurrentPage(newPage);
    };

    return (
        <div className="flex flex-col">
            <table className="table-auto mt-4">
                <thead>
                <tr>
                    <th className="px-4 py-2">ID</th>
                    <th className="px-4 py-2">Name</th>
                    <th className="px-4 py-2">Job Title</th>
                    <th className="px-4 py-2">Salary</th>
                </tr>
                </thead>
                <tbody>
                {employees.slice(startIndex, endIndex).map((employee) => (
                    <tr key={employee.id} className="bg-white">
                        <td className="px-4 py-2">{employee.id}</td>
                        <td className="px-4 py-2">{employee.name}</td>
                        <td className="px-4 py-2">{employee.jobTitle}</td>
                        <td className="px-4 py-2">{employee.salary} DH</td>
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

EmployeeTable.propTypes = {
    employees: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.number.isRequired,
            name: PropTypes.string.isRequired,
            jobTitle: PropTypes.string.isRequired,
            salary: PropTypes.number.isRequired,
        })
    ).isRequired,
};

export default EmployeeTable;
