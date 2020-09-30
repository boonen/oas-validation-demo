import React from 'react';
import './App.css';
import LocationIcon from '@material-ui/icons/LocationOn';

import {Admin, Resource} from 'react-admin';
import {LocationCreate, LocationEdit, LocationList} from './locations';
import jsonServerProvider from 'ra-data-json-server';

const dataProvider = jsonServerProvider('http://localhost:8080/api');
const App = () => (
    <Admin dataProvider={dataProvider}>
        <Resource name="locations" list={LocationList} edit={LocationEdit} create={LocationCreate} icon={LocationIcon}/>
    </Admin>
);


export default App;

