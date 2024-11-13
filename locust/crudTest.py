import random
from locust import HttpUser, task, between

"""
Spring failures:

Error report
# occurrences      Error                                                                                               
------------------|-------------------------------------------------------------------------------------------------------------------------------------------
Expected errors:
45                 PUT /petclinic/api/owners/1/pets/1: HTTPError('501 Server Error:  for url: /petclinic/api/owners/1/pets/1')
41                 POST /petclinic/api/pets: HTTPError('404 Client Error:  for url: /petclinic/api/pets')              
44                 POST /petclinic/api/visits: HTTPError('404 Client Error:  for url: /petclinic/api/visits')          
------------------|-------------------------------------------------------------------------------------------------------------------------------------------

"""

"""
Quarkus failures:

Error report
# occurrences      Error                                                                                               
------------------|-------------------------------------------------------------------------------------------------------------------------------------------
Expected errors:
35                 POST /petclinic/api/pets: HTTPError('400 Client Error: Bad Request for url: /petclinic/api/pets')   
43                 PUT /petclinic/api/owners/1/pets/1: HTTPError('405 Client Error: Method Not Allowed for url: /petclinic/api/owners/1/pets/1')

Check if solved:
28                 GET /petclinic/api/owners/1/pets/1: HTTPError('500 Server Error: Internal Server Error for url: /petclinic/api/owners/1/pets/1')

Have to be solved
35                 POST /petclinic/api/owners/1/pets: HTTPError('404 Client Error: Not Found for url: /petclinic/api/owners/1/pets')

Understand if wrong or not:
28                 POST /petclinic/api/owners: HTTPError('400 Client Error: Bad Request for url: /petclinic/api/owners')
    In this case name should be unique because is needed to find the owner
38                 POST /petclinic/api/pettypes: HTTPError('400 Client Error: Bad Request for url: /petclinic/api/pettypes')
    In this case name should be unique because is needed to find the type
33                 POST /petclinic/api/specialties: HTTPError('400 Client Error: Bad Request for url: /petclinic/api/specialties')
    In this case name should be unique because is needed to find the specialty
------------------|-------------------------------------------------------------------------------------------------------------------------------------------
"""

class PetClinicUser(HttpUser):
    wait_time = between(1, 3)
    base_path = "/petclinic/api"

    # NOTE: PetTypes CRUD tasks #######################################################
    # #########################################################################
    # #########################################################################
    @task
    def get_all_pettypes(self):
        self.client.get(f"{self.base_path}/pettypes")

    @task
    def get_pettype_by_id(self):
        self.client.get(f"{self.base_path}/pettypes/1")

    @task
    def update_pettype(self):
        updated_pettypes_data = {
            "name": "updated_pettype",
            "id": 1
        }
        self.client.put(f"{self.base_path}/pettypes/1", json=updated_pettypes_data)

    @task
    def addPettype(self):
        new_pettype_data= {
            "name":"new_pettype"
        }
        self.client.post(f"{self.base_path}/pettypes",json=new_pettype_data)

    # TODO: delete

    # NOTE: Owners CRUD tasks #################################################
    # #########################################################################
    # #########################################################################

    @task
    def get_owner_by_id(self):
        self.client.get(f"{self.base_path}/owners/1")

    @task
    def get_owner_pet_by_id(self):
        self.client.get(f"{self.base_path}/owners/1/pets/1")


    @task
    def get_that_owner_pets(self):
        owner_lastName = "Franklin"
        #NOTE: it's a query string
        self.client.get(f"{self.base_path}/owners?lastName={owner_lastName}",)

    # WARNING: owner lastName should be unique 
    @task
    def add_owner(self):
        new_owner_data = {
            "firstName": "newName",
            "lastName": "newLastname",
            "address": "newAddress",
            "city": "newCity",
            "telephone": "1"
        }
        self.client.post(f"{self.base_path}/owners", json=new_owner_data)

    @task
    def update_owner(self):
            updated_owner_data = {
                "firstName": "firstNameUpdated",
                "lastName": "lastNameUpdated",
                "address": "adressUpdated",
                "city": "cityUpdated",
                "telephone": "1"
            }
            self.client.put(f"{self.base_path}/owners/2", json=updated_owner_data)

    @task
    def update_owner_pet(self):
        updated_pet_data = {
            "name": "nameUpdateded",
            "birthDate": "2024-11-13",
            "type": {
                "name":"typeNameUpdated",
                "id":1
            }
        }
        self.client.put(f"{self.base_path}/owners/1/pets/1", json=updated_pet_data)


    @task
    def add_pet_to_owner(self):
        pet_data = {
            "name": "new_pet",
            "birthDate": "2024-11-13",
            "type": {
                "name": "useless_parameter",
                "id": 1
            }
        }
        self.client.post(f"{self.base_path}/owners/1/pets", json=pet_data)

    # NOTE: Vet tasks
    # #########################################################################
    # #########################################################################
    
    @task
    def get_vet_by_id(self):
        self.client.get(f"{self.base_path}/vets/1")

    @task
    def get_vets(self):
        self.client.get(f"{self.base_path}/vets")

    @task
    def add_vet(self):

        vet_data = {
            "firstName": "newFirstName",
            "lastName": "newLastname",
            "specialties": [
                {
                    "name": "radiology"
                }
            ]
        }
        self.client.post(f"{self.base_path}/vets",json = vet_data)

    @task
    def update_vet(self):

        vet_data = {
            "firstName": "firstNameUpdated",
            "lastName": "lastNameUpdated",
            "specialties": [
                {
                    "name": "radiology"
                }
            ]
        }
        self.client.put(f"{self.base_path}/vets/1",json= vet_data)


    # TODO: delete vet

    # NOTE: Pet tasks
    # #########################################################################
    # #########################################################################


    # WARNING: this method returns 404 in spring
    @task
    def add_pet(self):
        pet_data = {
            "name": "new_pet",
            "birthDate": "2024-11-13",
            "type": {
                "name": "useless_parameter",
                "id": 1
            }
        }
        self.client.post(f"{self.base_path}/pets", json=pet_data)

    @task
    def update_pet(self):
        pet_data = {
            "name": "updated_pet",
            "birthDate": "2024-11-13",
            "type": {
                "name": "useless_parameter",
                "id": 1
            }
        }
        self.client.put(f"{self.base_path}/pets/1", json=pet_data)

    @task
    def get_pet_by_id(self):
        self.client.get(f"{self.base_path}/pets/1")

    @task
    def get_pets(self):
        self.client.get(f"{self.base_path}/pets")

    # TODO: delete pet


    # NOTE: Specialties tasks
    # #########################################################################
    # #########################################################################

    @task
    def get_specialty_by_id(self):
        self.client.get(f"{self.base_path}/specialties/1")

    @task
    def get_specialties(self):
        self.client.get(f"{self.base_path}/specialties")

    @task
    def add_specialty(self):
        new_specialty_data = {
            "name": "new_specialty_name"
        }
        self.client.post(f"{self.base_path}/specialties", json=new_specialty_data)


    @task
    def update_specialty(self):
        updated_specialty_data = {
            "name": "updated_specialty_name"
        }
        self.client.put(f"{self.base_path}/specialties/2", json=updated_specialty_data)

    # TODO: delete specialty

    # NOTE: Visits tasks
    # #########################################################################
    # #########################################################################
    @task
    def get_visit_by_id(self):
        self.client.get(f"{self.base_path}/visits/1")

    @task
    def get_visits(self):
        self.client.get(f"{self.base_path}/visits")
    
    # WARNING: This in Spring is wrong
    @task
    def add_visit(self):
        new_visit_data = {
            "date": "2024-11-13",
            "description": "newDescription"
        }
        self.client.post(f"{self.base_path}/visits", json=new_visit_data)


    @task
    def update_visit(self):
        updated_visit_data = {
            "date": "2024-11-13",
            "description": "updated_description"
        }
        self.client.put(f"{self.base_path}/visits/1", json=updated_visit_data)


    @task
    def add_visit_to_owner_pet(self):
        new_visit_data = {
            "date": "2024-11-13",
            "description": "newDesription"
        }
        self.client.post(f"{self.base_path}/owners/1/pets/1/visits", json=new_visit_data)

    # TODO: delete visit
