from torchvision import transforms, datasets
from torch.utils.data import DataLoader


# transforms = {'train': transforms.Compose([transforms.Resize((224, 224)), transforms.ToTensor(),
#                                            transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])]),
#               'val': transforms.Compose([transforms.Resize((224, 224)), transforms.ToTensor(),
#                                          transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])])}
#
# train_dataset = datasets.ImageFolder(root='/content/hymenoptera_data/train')
# valid_dataset = datasets.ImageFolder(root='/content/hymenoptera_data/val')
# data_dir = '/content/hymenoptera_data'
# image_datasets = {x: datasets.ImageFolder(os.path.join(data_dir, x), transforms[x]) for x in ['train', 'val']}
# # train_dataset_=transform(train_dataset)
# # valid_dataset_=transform(valid_dataset)
# ds = {x: DataLoader(image_datasets[x], batch_size=BATCH, shuffle=True) for x in ['train', 'val']}


data_dir = '/home/vargha/Desktop/top'

train_transforms = transforms.Compose([transforms.RandomRotation(30),
                                      transforms.RandomResizedCrop(224),
                                      transforms.RandomHorizontalFlip(),
                                      transforms.ToTensor()])

test_transforms = transforms.Compose([transforms.RandomRotation(30),
                                     transforms.RandomResizedCrop(224),
                                     transforms.ToTensor()])


train_data = datasets.ImageFolder(data_dir + '/train', transform=train_transforms)
test_data = datasets.ImageFolder(data_dir + '/test', transform=test_transforms)

trainloader = torch.utils.data.DataLoader(train_data, batch_size=32)
testloader = torch.utils.data.DataLoader(test_data, batch_size=32)