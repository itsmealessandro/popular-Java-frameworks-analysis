import random
from locust import HttpUser, task, between

class PetClinicUser(HttpUser):
    wait_time = between(1, 3)
    base_path = "/petclinic/api"

    @task
    def pettypes_crud(self):
        """ NOTE: CRUD pettypes"""
        # GET ALL
        response = self.client.get(f"{self.base_path}/pettypes") 

        #GET
        pettypes = response.json()
        pettype = random.choice(pettypes)
        pettype_id = pettype.get("id") 

        self.client.get(f"{self.base_path}/pettypes/{pettype_id}")

        #PUT
        updated_pettypes_data = {
            "name": f"{pettype.get('name')}_updated",
            "id": f"{pettype_id}"
        }
        self.client.put(f"{self.base_path}/pettypes",updated_pettypes_data)
        #PUT CHECK
        self.client.get(f"{self.base_path}/pettypes/{pettype_id}")

        #POST
        inserted_pettypes_data = {
            "name:new_pettype"
        }
        self.client.post(f"{self.base_path}/pettypes",inserted_pettypes_data)

        #DELETE
        self.client.delete(f"{self.base_path}/pettypes/{pettype_id}")



    @task
    def owner_crud(self):
        """ NOTE: CRUD owner"""
        # GET ALL
        response = self.client.get(f"{self.base_path}/owners")

        # GET
        if response.status_code != 200 or not response.json():
            return  # should be an error

        owners = response.json()
        owner = random.choice(owners)
        owner_id = owner.get("id")

        self.client.get(f"{self.base_path}/owners/{owner_id}")

        # PUT
        updated_owner_data = {
            "firstName": f"{owner['firstName']}_updated",
            "lastName": owner["lastName"],
            "address": owner["address"],
            "city": owner["city"],
            "telephone": owner["telephone"],
            "id": owner_id
        }
        self.client.put(f"{self.base_path}/owners", json=updated_owner_data)

        # POST
        new_owner_data = {
            "firstName": "New",
            "lastName": "Owner",
            "address": "123 New St.",
            "city": "New City",
            "telephone": "1234567890"
        }
        self.client.post(f"{self.base_path}/owners", json=new_owner_data)

        # DELETE
        self.client.delete(f"{self.base_path}/owners/{owner_id}")


    @task
    def pet_crud(self):
        """ NOTE: CRUD pet"""
        # GET ALL
        response = self.client.get(f"{self.base_path}/pets")

        # GET
        if response.status_code != 200 or not response.json():
            return  # should be an error

        pets = response.json()
        pet = random.choice(pets)
        pet_id = pet.get("id")

        self.client.get(f"{self.base_path}/pets/{pet_id}")

        # PUT
        updated_pet_data = {
            "name": f"{pet['name']}_updated",
            "birthDate": pet["birthDate"],
            "type": pet["type"],
            "id": pet_id
        }
        self.client.put(f"{self.base_path}/pets", json=updated_pet_data)

        # POST
        new_pet_data = {
            "name": "Buddy",
            "birthDate": "2022-01-01",
            "type": {"id": 1, "name": "Dog"}  # Assuming you have a pet type with id 1
        }
        self.client.post(f"{self.base_path}/pets", json=new_pet_data)

        # DELETE
        self.client.delete(f"{self.base_path}/pets/{pet_id}")


    @task
    def vet_crud(self):
        """ NOTE: CRUD vet"""
        # GET ALL
        response = self.client.get(f"{self.base_path}/vets")

        # GET
        if response.status_code != 200 or not response.json():
            return  # should be an error

        vets = response.json()
        vet = random.choice(vets)
        vet_id = vet.get("id")

        self.client.get(f"{self.base_path}/vets/{vet_id}")

        # PUT
        updated_vet_data = {
            "firstName": f"{vet['firstName']}_updated",
            "lastName": vet["lastName"],
            "specialties": vet["specialties"],
            "id": vet_id
        }
        self.client.put(f"{self.base_path}/vets", json=updated_vet_data)

        # POST
        new_vet_data = {
            "firstName": "Sara",
            "lastName": "Smith",
            "specialties": [{"id": 1, "name": "Surgery"}]  # Assuming specialty with id 1
        }
        self.client.post(f"{self.base_path}/vets", json=new_vet_data)

        # DELETE
        self.client.delete(f"{self.base_path}/vets/{vet_id}")



    @task
    def specialty_crud(self):
        """ NOTE: CRUD specialty"""
        # GET ALL
        response = self.client.get(f"{self.base_path}/specialties")

        # GET
        if response.status_code != 200 or not response.json():
            return  # should be an error

        specialties = response.json()
        specialty = random.choice(specialties)
        specialty_id = specialty.get("id")

        self.client.get(f"{self.base_path}/specialties/{specialty_id}")

        # PUT
        updated_specialty_data = {
            "name": f"{specialty['name']}_updated",
            "id": specialty_id
        }
        self.client.put(f"{self.base_path}/specialties", json=updated_specialty_data)

        # POST
        new_specialty_data = {
            "name": "Dentistry"
        }
        self.client.post(f"{self.base_path}/specialties", json=new_specialty_data)

        # DELETE
        self.client.delete(f"{self.base_path}/specialties/{specialty_id}")


    @task
    def visit_crud(self):
        """ NOTE: CRUD visit"""
        # GET ALL
        response = self.client.get(f"{self.base_path}/visits")

        # GET
        if response.status_code != 200 or not response.json():
            return  # should be an error

        visits = response.json()
        visit = random.choice(visits)
        visit_id = visit.get("id")

        self.client.get(f"{self.base_path}/visits/{visit_id}")

        # PUT
        updated_visit_data = {
            "date": "2024-10-01",
            "description": "Updated visit description",
            "id": visit_id
        }
        self.client.put(f"{self.base_path}/visits", json=updated_visit_data)

        # POST
        new_visit_data = {
            "date": "2024-10-10",
            "description": "Annual check-up",
            "petId": 1  # Assuming pet with id 1 exists
        }
        self.client.post(f"{self.base_path}/visits", json=new_visit_data)

        # DELETE
        self.client.delete(f"{self.base_path}/visits/{visit_id}")
