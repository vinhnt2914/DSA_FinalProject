
import React from "react";
import { Popover, PopoverTrigger, PopoverContent, Button } from "@nextui-org/react";
import { MdOutlineBugReport } from "react-icons/md";


/**
 * Renders the debug popover providing useful information about the
 * current positions and relevant parameters.
 * @returns {JSX.Element} The DebugMode component.
 */
export default function DebugMode() {
	// Content of the popover
	const content = (
		<PopoverContent>
			<div className="px-1 py-2">
				<div className="text-medium font-bold "><MdOutlineBugReport /> Debug info</div>
				<div className="text-small">[CENTER] Latitude: </div>
				<div className="text-small">[CENTER] Longtitude: </div>
				{/* Count active markers */}
				<div className="text-small">[MARKERS] Active markers: </div>
				{/* Count active services */}
				<div className="text-small">[SERVICES] Active services: </div>
				{/* The rectangle bound details */}
				<div className="text-small">[BOUND] Rectangle bound: </div>
				{/* The current zoom level */}
				<div className="text-small">[ZOOM] Current zoom: </div>
			</div>
		</PopoverContent>
	);
	// Placement of the popover
	const placements = [
		"top-end",
	];

	return (
		<div className="flex flex-wrap md:inline-grid md:grid-cols-1 gap-4">
			{placements.map((placement) => (
				<Popover key={placement} placement={placement} color="secondary">
					<PopoverTrigger>
						<Button color="secondary" variant="shadow"
							isIconOnly={true}
							className="capitalize">
							<MdOutlineBugReport />
						</Button>
					</PopoverTrigger>
					{content}
				</Popover>
			))}
		</div>
	);
}
