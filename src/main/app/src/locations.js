import * as React from "react";
import {Create, Datagrid, Edit, Filter, List, SimpleForm, TextField, TextInput} from 'react-admin';

const LocationTitle = ({record}) => {
    return <span>Location: {record ? `${record.id}` : ''}</span>;
};

const LocationFilter = (props) => (
    <Filter {...props}>
        <TextInput label="Search" source="q" alwaysOn/>
    </Filter>
);

export const LocationList = props => (
    <List filters={<LocationFilter/>} {...props}>
        <Datagrid rowClick="edit">
            <TextField source="api.name"/>
            <TextField source="api.version"/>
            <TextField source="id"/>
        </Datagrid>
    </List>
);

export const LocationEdit = props => (
    <Edit title={<LocationTitle/>} {...props}>
        <SimpleForm>
            <TextInput disabled source="id"/>
            <TextInput source="api.name"/>
            <TextInput source="api.version"/>
        </SimpleForm>
    </Edit>
);

export const LocationCreate = props => (
    <Create {...props}>
        <SimpleForm>
            <TextInput source="id"/>
            <TextInput source="api.name"/>
            <TextInput source="api.version"/>
        </SimpleForm>
    </Create>
);