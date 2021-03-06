cmake_minimum_required(VERSION 3.0.0)
project(universalapitester CXX)
include(CheckCXXCompilerFlag)

# Let's build shared libs (*.so)
OPTION(BUILD_SHARED_LIBS "Build shared libraries." ON)

# Add any other header files
include_directories(${CMAKE_SOURCE_DIR}/lib)
include_directories(${CMAKE_SOURCE_DIR}/src/headers)
include_directories(${CMAKE_SOURCE_DIR}/src/common)
include_directories(${CMAKE_SOURCE_DIR}/src/api)

#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -I ${CMAKE_SOURCE_DIR}/src/headers")

# Detect compiler and act accordingly
if ("${CMAKE_CXX_COMPILER_ID}" STREQUAL "Clang" OR "${CMAKE_CXX_COMPILER_ID}" STREQUAL "GNU")
	option(CMAKE_CXX_VERBOSE "Verbose compilation output?" OFF)
	option(CMAKE_LD_VERBOSE "Verbose linking output?" OFF)

	if(CMAKE_CXX_VERBOSE)
		set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} --verbose")
	endif()
	if(CMAKE_LD_VERBOSE)
		set(CMAKE_EXE_LINKER_FLAGS "-Wl,--as-needed -Wl,--trace -Wl,--threads -Wl,--stats ${CMAKE_EXE_LINKER_FLAGS}")
	endif()

	# Enable all warnings
	check_cxx_compiler_flag(-Wall HAVE_WALL)
	if (HAVE_WALL)
		set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall")
	endif()

	# First check for C++17
	# If not check for C++14 as fallback
	check_cxx_compiler_flag(-std=c++17 COMPILER_SUPPORTS_CXX17)
	if(COMPILER_SUPPORTS_CXX17)
		set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++17")
	elseif(NOT COMPILER_SUPPORTS_CXX17)
		check_cxx_compiler_flag(-std=c++14 COMPILER_SUPPORTS_CXX14)
		if(COMPILER_SUPPORTS_CXX14)
			set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++14")
		endif()
	else()
		message(STATUS "The compiler ${CMAKE_CXX_COMPILER} has no C++17 or 14 support. Please use a different C++ compiler.")
	endif()
endif()

#find_package(package_name version REQUIRED)
#find_package( Boost 1.60 COMPONENTS system filesystem REQUIRED )
#include_directories( ${Boost_INCLUDE_DIR} )

add_subdirectory(src)
