"use client";
import { useState } from "react";


export default function ChatBotPage() {
    const [prompt, setPrompt] = useState('');
    const [response, setResponse] = useState<object | null>(null);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      setLoading(true);


      setTimeout(() => {
        setResponse({
          prompt,
          reply: "This is a mock AI response to your prompt.",
          timestamp: new Date().toISOString()
        });
        setLoading(false);
      }, 1000);
    };
    return (
         <div className="min-h-screen bg-gray-100 flex items-center justify-center p-4">
      <div className="bg-white p-6 rounded-2xl shadow-md w-full max-w-2xl">
        <h1 className="text-2xl font-bold mb-4">AI ChatBot</h1>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            placeholder="Enter your prompt..."
            value={prompt}
            onChange={(e) => setPrompt(e.target.value)}
            className="w-full p-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
          <button
            type="submit"
            disabled={loading || !prompt}
            className="bg-blue-500 text-white px-4 py-2 rounded-xl hover:bg-blue-600 disabled:opacity-50"
          >
            {loading ? 'Sending...' : 'Send Prompt'}
          </button>
        </form>

        <div className="mt-6">
          <h2 className="text-lg font-semibold mb-2">Response (JSON):</h2>
          <pre className="bg-gray-100 p-4 rounded-xl overflow-x-auto text-sm text-gray-800">
            {response ? JSON.stringify(response, null, 2) : 'No response yet.'}
          </pre>
        </div>
      </div>
    </div>
    );
}