// Essential for the map to work
// including react and react-leaflet
import React, { Component } from "react";
// Individual components
import {
	MapContainer,
	Marker,
	Popup,
	ZoomControl,
	ImageOverlay,
} from "react-leaflet";
import "../App.css";
import { CRS } from "leaflet";
import MapCustom from "./MapCustom";
import MapCustom2 from "./MapCustom2";



/**
 * The Map component.
 * @extends Component
 * @param {Object} props - The props of the component.
 * @param {Object} props.state - The state of the component.
 * @param {Number} props.state.lat - The latitude of the map.
 * @param {Number} props.state.lng - The longitude of the map.
 * @param {Number} props.state.zoom - The zoom level of the map.
 * @param {Array} props.state.markers - The markers to be displayed on the map.
 * @returns {JSX.Element} The rendered Map component.
 * 
 **/
class Map extends Component {
	constructor(props) {
		super(props);
		this.state = {
			lat: 51.505,
			lng: +0.09,
			zoom: 0.45,
		};
		// this.moveTo = this.moveTo.bind(this);
		this.setCenter = this.setCenterProps.bind(this);
		this.listOfMarkers = [];
	}

	componentDidUpdate(prevProps) {
		if (this.props.state !== prevProps.state) {
			this.setCenterProps(this.props.state.lat, this.props.state.lng, this.props.state.zoom);
			// if the data also include markers
			// put them on the map
			if (this.props.markers) {
				this.fetchMarkers(this.props.state.markers);
			}
		}

	}

	// TODO: Get a list of nearby places 
	// TODO: Get a rectangle bound

	setCenterProps(longitude, latitude, zoom) {
		this.setState({
			lat: latitude,
			lng: longitude,
			zoom: zoom,
		});
		console.log("Set center to: ", longitude, latitude, zoom);
	}

	/**
	 * Fetches markers from JSON data and adds them to the map.
	 * @param {Array} JSONData - The JSON data containing marker information.
	 */
	fetchMarkers(JSONData) {
		JSONData.forEach((marker) => {
			this.listOfMarkers.push(
				<Marker position={[marker.lat, marker.lng]}>
					<Popup>{marker.name}</Popup>
				</Marker>
			);
		});
	}

	/**
	 * Renders the Map component.
	 * @returns {JSX.Element} The rendered Map component.
	 */
	render() {
		// Diagonal start and end position define the area of the map
		const bounds = [[0, 0], [10000000, 10000000]];
		console.log(
			"Current state: ",
			this.state.lat,
			this.state.lng,
			this.state.zoom
		);
		return (
			<MapContainer
				center={[this.state.lat, this.state.lng]}
				zoom={this.state.zoom}
				className="h-dvh z-10"
				bounds={bounds}
				zoomControl={false}
				minZoom={-1000}
				maxZoom={0.45}
				crs={CRS.Simple}
				zoomSnap={0.1}
				zoomDelta={0.1}
			>
				{/* Rectangle bound */}
				<MapCustom2 />
				{/* Map that moves based on the position */}
				<MapCustom lat={this.state.lat} lng={this.state.lng} zoom={this.state.zoom} />
				<ImageOverlay
					url="https://preview.redd.it/ih6no69aj90y.png?auto=webp&s=8e4f9101f58e0812f3625a51ec65c9b7c050da75"
					bounds={bounds}
					className="border box-border border-black"
				/>
				<ZoomControl position="bottomleft" />
				<Marker position={[this.state.lat, this.state.lng]}>
					<Popup>This is the center</Popup>
				</Marker>
			</MapContainer>
		);
	}
}

export default Map;