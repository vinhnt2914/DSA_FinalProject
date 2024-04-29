import { useMap } from 'react-leaflet';

export default function MapCustom({ lat, lng, zoom }) {
    const map = useMap();
    const center = [lat, lng]
    map.setView(center, zoom);
    // rerender the map
    return null;
}