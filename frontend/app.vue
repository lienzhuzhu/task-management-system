<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Navigation -->
    <nav class="bg-white shadow-sm border-b border-gray-200">
      <div class="container mx-auto px-4">
        <div class="flex items-center justify-between h-16">
          <div class="flex items-center space-x-8">
            <NuxtLink to="/" class="text-xl font-bold text-gray-900">
              Task Manager
            </NuxtLink>
            <div class="flex space-x-4">
              <NuxtLink
                to="/"
                class="text-gray-700 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium"
                active-class="bg-gray-100 text-gray-900"
              >
                Dashboard
              </NuxtLink>
              <NuxtLink
                to="/users"
                class="text-gray-700 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium"
                active-class="bg-gray-100 text-gray-900"
              >
                Users
              </NuxtLink>
              <NuxtLink
                to="/tasks"
                class="text-gray-700 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium"
                active-class="bg-gray-100 text-gray-900"
              >
                Tasks
              </NuxtLink>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <!-- Page Content -->
    <main>
      <NuxtPage />
    </main>
  </div>
</template>
