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

        pettypes = response.json()
        pettype = random.choice(pettypes)
        pettype_id = pettype.get("id")

        # GET
        self.client.get(f"{self.base_path}/pettypes/{pettype_id}")

        # PUT
        updated_pettypes_data = {
            "name": f"{pettype.get('name')}_updated"
        }
        self.client.put(f"{self.base_path}/pettypes/{pettype_id}", json=updated_pettypes_data)

        # POST
        inserted_pettypes_data = {
            "name": "new_pettype"
        }
        self.client.post(f"{self.base_path}/pettypes", json=inserted_pettypes_data)

        # DELETE
        self.client.delete(f"{self.base_path}/pettypes/{pettype_id}")



    @task
    def owner_crud(self):
        """ NOTE: CRUD owner"""
        # GET ALL
        response = self.client.get(f"{self.base_path}/owners")

        # GET
        owners = response.json()
        owner = random.choice(owners)
        owner_id = owner.get("id")

        self.client.get(f"{self.base_path}/owners/{owner_id}")


    @task
    def pet_crud(self):
        """ NOTE: CRUD pet"""
        # GET ALL
        response = self.client.get(f"{self.base_path}/pets")

        pets = response.json()
        pet = random.choice(pets)
        pet_id = pet.get("id")

        # GET
        self.client.get(f"{self.base_path}/pets/{pet_id}")

        # PUT
        updated_pet_data = {
            "name": f"{pet['name']}_updated",
            "birthDate": pet["birthDate"],
            "type": pet["type"]
        }
        self.client.put(f"{self.base_path}/pets/{pet_id}", json=updated_pet_data)

        # POST
        new_pet_data = {
            "name": "Buddy",
            "birthDate": "2022-01-01",
            "type": {"id": 1, "name": "Dog"}  # Assumendo che il tipo di animale con ID 1 esista
        }
        self.client.post(f"{self.base_path}/pets", json=new_pet_data)

        # DELETE
        self.client.delete(f"{self.base_path}/pets/{pet_id}")


    @task
    def vet_crud(self):
        """ NOTE: CRUD vet"""
        # GET ALL
        response = self.client.get(f"{self.base_path}/vets")

        vets = response.json()
        vet = random.choice(vets)
        vet_id = vet.get("id")

        # GET
        self.client.get(f"{self.base_path}/vets/{vet_id}")

        # PUT
        updated_vet_data = {
            "firstName": f"{vet['firstName']}_updated",
            "lastName": vet["lastName"],
            "specialties": vet["specialties"]
        }
        self.client.put(f"{self.base_path}/vets/{vet_id}", json=updated_vet_data)

        # POST
        new_vet_data = {
            "firstName": "Sara",
            "lastName": "Smith",
            "specialties": [{"id": 1, "name": "Surgery"}]  # Assumendo che la specializzazione con ID 1 esista
        }
        self.client.post(f"{self.base_path}/vets", json=new_vet_data)

        # DELETE
        self.client.delete(f"{self.base_path}/vets/{vet_id}")



    @task
    def specialty_crud(self):
        """ NOTE: CRUD specialty"""
        # GET ALL
        response = self.client.get(f"{self.base_path}/specialties")

        specialties = response.json()
        specialty = random.choice(specialties)
        specialty_id = specialty.get("id")

        # GET
        self.client.get(f"{self.base_path}/specialties/{specialty_id}")

        # PUT
        updated_specialty_data = {
            "name": f"{specialty['name']}_updated"
        }
        self.client.put(f"{self.base_path}/specialties/{specialty_id}", json=updated_specialty_data)

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

        visits = response.json()
        visit = random.choice(visits)
        visit_id = visit.get("id")

        # GET
        self.client.get(f"{self.base_path}/visits/{visit_id}")

        # PUT
        updated_visit_data = {
            "date": "2024-10-01",
            "description": "Updated visit description"
        }
        self.client.put(f"{self.base_path}/visits/{visit_id}", json=updated_visit_data)

        # POST
        new_visit_data = {
            "date": "2024-10-10",
            "description": "Annual check-up",
            "petId": 1  # Assumendo che l'animale con ID 1 esista
        }
        self.client.post(f"{self.base_path}/visits", json=new_visit_data)

        # DELETE
        self.client.delete(f"{self.base_path}/visits/{visit_id}")
