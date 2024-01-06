import PropTypes from "prop-types";

const RootLayout = ({ children }) => {
    return (
        <div className="min-h-screen flex flex-col bg-gray-100">
            {/* Header */}
            <header className="bg-blue-500 text-white p-4">
                <div className="container mx-auto">
                    <h1 className="text-2xl font-bold">Employee Management App</h1>
                </div>
            </header>

            {/* Main Content */}
            <main className="flex-grow container mx-auto p-4">
                {children}
            </main>

        </div>
    );
};

RootLayout.propTypes = {
    // title: PropTypes.string.isRequired,
    children: PropTypes.node.isRequired,
};

export default RootLayout;
