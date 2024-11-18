from os import walk
import random, time 
from locust import HttpUser, task, between

def action_prob(prob):
    """
    @param prob: int - The minimum value (1-100) that the randomly generated number should meet or exceed.
    @return: bool - True if the random number (in the range of 1 to 100) is less than or equal to `prob`; otherwise, False.
    @raises ValueError: If `prob` is not within the valid range (1-100).
    Returns true if the random number is higher than the given parameter in a range of 1-100
    """
    if(random.randrange(1,100) <= prob):
        return False
    return True


class PetClinicUser(HttpUser):
    wait_time = between(1, 3)
    base_path = "/petclinic/api"


    ADD_OWNER_PATH= "./addOwner_counter.txt"
    ADD_PETTYPE_PATH= "./addPettype_counter.txt"
    ADD_SPECIALTY_PATH="./addSpecialty_counter.txt"
    UPDATE_OWNER_PATH="./updateOwner_counter.txt"
    UPDATE_PETTYPE_PATH="./updatePettype_counter.txt"
    UPDATE_SPECIALTY_PATH="./updateSpecialty_counter.txt"

    def getUpdateCounter(self,file_path):
        """ get the counter from the specified file path, ovverrides it increasing it by 1"""
        file = open(file_path,"r+")

        rows= file.readlines()
        value = int(rows[0].strip())
        rows[0]= str(value+1)

        file.seek(0)
        file.writelines(rows);
        file.close()
        return value

    def getUniqueString(self,value):
        letters = "abcdefghijklmnopqrstuvwxyz"  
        base26=""
        base = len(letters)
        while value >= 0:
            value, resto = divmod(value, base)
            base26 = letters[resto] + base26
            if value == 0:
                break
            value -= 1  # avoid some problems
        return base26

    @task
    def user1_CRUDE_pet(self):
        """
        adds a new owner,
        adds a new pet to that owner,
        updates the owner data,
        """

        owner_counter = self.getUpdateCounter(self.ADD_OWNER_PATH)
        # adds a new owner
        owner_unique = self.getUniqueString(owner_counter)
        new_owner_data = {
            "firstName": "newName",
            "lastName": f"new{owner_unique}",
            "address": "newAddress",
            "city": "newCity",
            "telephone": "1"
        }
        self.client.post(f"{self.base_path}/owners", json=new_owner_data)

        # adds a new pet to that owner

        pet_data = {
            "name": "newPet",
            "birthDate": "2024-11-13",
            "type": {
                "name": "useless_parameter",
                "id": 1
            }
        }

        self.client.post(f"{self.base_path}/owners/1/pets", json=pet_data)

        # udpates owner data

        owner_counter = self.getUpdateCounter(self.ADD_OWNER_PATH)
        owner_unique = self.getUniqueString(owner_counter)
        updated_owner_data = {
            "firstName": "updatedName",
            "lastName": f"updated{owner_unique}",
            "address": "updatedAddress",
            "city": "updatedCity",
            "telephone": "1"
        }
        self.client.put(f"{self.base_path}/owners/2", json=updated_owner_data)


    @task
    def user2_CRUDE_pet(self):
        """
        adds a new pet to an existing owner
        update pet data
        """

        # adds pet

        pet_data = {
            "name": "newPet",
            "birthDate": "2024-11-13",
            "type": {
                "name": "uselessParameter",
                "id": 1
            }
        }
        self.client.post(f"{self.base_path}/owners/1/pets", json=pet_data)

        # update pet

        updated_pet_data = {
            "name": "updatedPet",
            "birthDate": "2024-11-12",
            "type": {
                "name": "uselessParameter",
                "id": 1
            }
        }
        self.client.put(f"{self.base_path}/pets/2", json=updated_pet_data)


    @task
    def user3_CRUDE_type(self):
        """
        adds new type
        updates a type
        """

        # adds type
        pettype = self.getUpdateCounter(self.ADD_PETTYPE_PATH)

        new_pettype_data= {
            "name":f"newPettype{pettype}"
        }

        self.client.post(f"{self.base_path}/pettypes",json=new_pettype_data)

        #updates a type


        updated_pettypes_data = {
            "name": "updatedPettype",
            "id": 2
        }
        self.client.put(f"{self.base_path}/pettypes/2", json=updated_pettypes_data)


    @task
    def user4_CRUDE_vet(self):
        """
        adds new vet,
        updates vet

        """
        # adds vet
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

        # update vet

        update_vet_data = {
            "firstName": "firstNameUpdated",
            "lastName": "lastNameUpdated",
            "specialties": [
                {
                    "name": "radiology"
                }
            ]
        }
        self.client.put(f"{self.base_path}/vets/2",json= update_vet_data)


    @task
    def user5_CRUDE_Visit(self):
        """
        adds a visit
        update a visit
        """
        #adds visit

        new_visit_data = {
            "date": "2024-11-13",
            "description": "newDesription"
        }
        self.client.post(f"{self.base_path}/owners/1/pets/1/visits", json=new_visit_data)

        #update visit

        updated_visit_data = {
            "date": "2024-11-13",
            "description": "updatedDescription"
        }
        self.client.put(f"{self.base_path}/visits/2", json=updated_visit_data)

    @task
    def user6_CRUDE_Specialty(self):
        """
        adds a specialty
        update a specialty
        """

        # adds a specialty
        specialty= self.getUpdateCounter(self.ADD_SPECIALTY_PATH)
        new_specialty_data = {
            "name": f"new_specialty_name{specialty}"
        }
        self.client.post(f"{self.base_path}/specialties", json=new_specialty_data)

        #update specialty

        unique_specialty = self.getUniqueString(specialty)
        updated_specialty_data = {
            "name": f"updated_{unique_specialty}"
        }
        self.client.put(f"{self.base_path}/specialties/2", json=updated_specialty_data)




