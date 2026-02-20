/**
 * API Client Composable
 *
 * Provides methods to make HTTP requests to the backend API.
 * Handles error responses and converts them to a consistent format.
 */
export const useApiClient = () => {
  const apiBaseUrl = 'http://localhost:8080/api'

  /**
   * Make a GET request
   */
  async function get(endpoint: string) {
    try {
      const response = await fetch(`${apiBaseUrl}${endpoint}`)

      if (!response.ok) {
        await handleErrorResponse(response)
      }

      return await response.json()
    } catch (error) {
      throw error
    }
  }

  /**
   * Make a POST request
   */
  async function post(endpoint: string, data: any) {
    try {
      const response = await fetch(`${apiBaseUrl}${endpoint}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      })

      if (!response.ok) {
        await handleErrorResponse(response)
      }

      return await response.json()
    } catch (error) {
      throw error
    }
  }

  /**
   * Make a PUT request
   */
  async function put(endpoint: string, data: any) {
    try {
      const response = await fetch(`${apiBaseUrl}${endpoint}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      })

      if (!response.ok) {
        await handleErrorResponse(response)
      }

      return await response.json()
    } catch (error) {
      throw error
    }
  }

  /**
   * Make a DELETE request
   */
  async function deleteRequest(endpoint: string) {
    try {
      const response = await fetch(`${apiBaseUrl}${endpoint}`, {
        method: 'DELETE',
      })

      if (!response.ok) {
        await handleErrorResponse(response)
      }

      // DELETE may return 204 No Content, so don't try to parse JSON
      if (response.status === 204) {
        return null
      }

      return await response.json()
    } catch (error) {
      throw error
    }
  }

  /**
   * Handle error responses from the API
   */
  async function handleErrorResponse(response: Response) {
    let errorMessage = `HTTP ${response.status}: ${response.statusText}`
    let validationErrors = null

    try {
      const errorData = await response.json()

      // Handle validation errors (from GlobalExceptionHandler)
      if (errorData.errors) {
        validationErrors = errorData.errors
        errorMessage = errorData.message || 'Validation failed'
      } else if (errorData.message) {
        errorMessage = errorData.message
      }
    } catch (e) {
      // If response is not JSON, use default error message
    }

    const error: any = new Error(errorMessage)
    error.status = response.status
    error.errors = validationErrors
    throw error
  }

  return {
    get,
    post,
    put,
    delete: deleteRequest,
  }
}
