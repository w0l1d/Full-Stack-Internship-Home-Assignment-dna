'use client'
const BASE_URL = 'http://127.0.0.1:8080/api/employees';

const CsvService = {
    uploadCsv: async (formData) => {
        const response = await fetch(`${BASE_URL}/upload`, {
            method: 'POST',
            body: formData,
        });
        return await response.json();
    },

    // getSummary: async () => {
    //     const response = await fetch(`${BASE_URL}/summary`);
    //     return await response.json();
    // },

    getSummary: async (employees) => {
        const response = await fetch(`${BASE_URL}/summary`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(employees),
        });
        if (!response.ok) {
            throw new Error('Something went wrong');
        }
        return await response.json();
    },
};

export default CsvService;
