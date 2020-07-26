/*
* <one line to give the library's name and an idea of what it does.>
* Copyright (C) 2020  Lukas zurschmiede <lukas.zurschmiede@ranta.ch>
* 
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

#include<getopt.h>

#include "app/Application.h"

static common::Logger * _universal_api_logger = new common::Logger();

void usage(char *name) {
	std::cout << "Usage: " << name << " -i|--in FILE [-o|--out FILE] [-v|--verbose]"  << std::endl;
	exit(EXIT_FAILURE);
}

int main(int argc, char** argv) {
	std::string in;
	std::string out;

	int c = 0;
	while (c >= 0) {
		static char _short[] = "-i:o:v";
		static struct option _options[] = {
			{"in",      required_argument, 0, 'i'},
			{"out",     required_argument, 0, 'o'},
			{"verbose", no_argument,       0, 'v'},
			{0, 0, 0, 0}
		};

		int option_index = 0;
		c = getopt_long(argc, argv, _short, _options, &option_index);

		if (argc == 1)
			c = '?';

		switch(c) {
			case 'i':
				in.append(optarg);
				break;

			case 'o':
				out.append(optarg);
				break;

			case 'v':
				_universal_api_logger->setLevel(common::Logger::VERBOSE);
				break;

			case 1:
			case '?':
				usage(argv[0]);
		}
	}

	return( Tester::Application(in, out, _universal_api_logger).run() );
}
